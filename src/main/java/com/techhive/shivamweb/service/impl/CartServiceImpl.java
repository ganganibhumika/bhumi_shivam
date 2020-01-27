package com.techhive.shivamweb.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForCartStatus;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.DTO.UserDto;
import com.techhive.shivamweb.model.Cart;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Wishlist;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.ConfirmOrderRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.repository.WishlistRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.response.payload.UserWiseCartDetail;
import com.techhive.shivamweb.service.CartService;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ConfirmOrderRepository confirmOrderRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	WishlistRepository wishlistRepository;

	Boolean confirmOrderFlag = false;

	@Autowired
	FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	double totalWeight = 0;
	double total = 0;
	double totalRapRate = 0;
	DecimalFormat df = new DecimalFormat("####0.00");
	List<Double> disc;

	@Override
	public ResponseWrapperDTO saveCart(MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		String userId = body.getUserId();
		List<Cart> carts = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<Cart>>() {
				});
		if (ShivamWebMethodUtils.isListNullOrEmpty(carts))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);
		StringBuilder result = new StringBuilder();
		Set<String> confirmOrderList = new HashSet<>();
		Set<String> inWishList = new HashSet<>();
		Set<String> addedToWishList = new HashSet<>();
		Set<String> othersConfirm = new HashSet<>();
		// Set<String> stoneUnderBuisnessProcess = new HashSet<>();
		Set<String> addToCart = new HashSet<>();
		Set<String> alreadyInCart = new HashSet<>();
		confirmOrderFlag = false;
		carts.forEach(cart -> {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(cart.getPktMasterId())) {
				Optional<PktMaster> pktMaster = pktMasterRepository.findById(cart.getPktMasterId());
				if (pktMaster.isPresent()) {
					if (pktMaster.get().getIsSold() == true) {
						// your confirm order
						if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.get().getConfirmOrder())
								&& pktMaster.get().getConfirmOrder().getUser().getId().equals(userId)) {
							confirmOrderList.add(pktMaster.get().getStoneId());

						} else
						// other's confirm order
						{
							othersConfirm.add(pktMaster.get().getStoneId());
						}
					} else {
						if (pktMaster.get().getIsHold() == true) {
							// add to wish list
							// if not present in wishlist than only

							Optional<Wishlist> wishlistOfUser = wishlistRepository.findByUserAndPkt(userId,
									cart.getPktMasterId());
							if (!wishlistOfUser.isPresent()) {
								Wishlist wishlist = new Wishlist();
								wishlist.setUser(user.get());
								wishlist.setPktMaster(pktMaster.get());
								wishlistRepository.saveAndFlush(wishlist);
								addedToWishList.add(pktMaster.get().getStoneId());
							}
							inWishList.add(pktMaster.get().getStoneId());
						} else {
							Optional<Cart> cartOfUser = cartRepository.findByUserAndPkt(userId, cart.getPktMasterId());
							if (cartOfUser.isPresent()) {
								alreadyInCart.add(pktMaster.get().getStoneId());
							} else {
								cart.setUser(user.get());
								cart.setPktMaster(pktMaster.get());
								cartRepository.saveAndFlush(cart);
								addToCart.add(pktMaster.get().getStoneId());
								/// remove from wishlist
								Optional<Wishlist> wishlistOfUser = wishlistRepository.findByUserAndPkt(userId,
										cart.getPktMasterId());
								if (wishlistOfUser.isPresent()) {
									wishlistRepository.deleteById(wishlistOfUser.get().getId());
								}
							}
						}
					}
				}
			}
		});
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(confirmOrderList) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(confirmOrderList.toString())
						+ " already Confirmed.\n ");
		// result.append(ShivamWebMethodUtils.isSetNullOrEmpty(stoneUnderBuisnessProcess)
		// ? ""
		// :
		// ShivamWebMethodUtils.remove1StAndLastCharOfString(stoneUnderBuisnessProcess.toString())
		// + "stone(s) under buisness process.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(inWishList) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(inWishList.toString())
						+ " under buisness process see in your Wish list.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersConfirm) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersConfirm.toString())
						+ " this stone(s) have been confirmed by other.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(alreadyInCart) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(alreadyInCart.toString())
						+ " already in your Cart.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(addToCart) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(addToCart.toString()) + " added to Cart. ");
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(addToCart)) {
			userTrackingService.saveTracking(user.get(), request.getRemoteAddr(),
					EnumForUserTracking.STONEID.toString()
							+ ShivamWebMethodUtils.remove1StAndLastCharOfString(addToCart.toString())
							+ EnumForUserTracking.STONEADDEDTOCART.toString());
		}
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(addedToWishList)) {
			userTrackingService.saveTracking(user.get(), request.getRemoteAddr(),
					EnumForUserTracking.STONEID.toString()
							+ ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToWishList.toString())
							+ EnumForUserTracking.STONEADDEDTOWISHLIST.toString());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, result.toString(), null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllCart(String userId, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		List<Cart> list = cartRepository.findAllByUser(userId);
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
			for (Cart cart : list) {
				disc = new ArrayList<>();
				getDiscount(cart);
			}
			double finalTCTS = totalWeight;
			double finalDiscountPercentage = (1 - (total / totalRapRate)) * 100;

			double finalRapRate = totalRapRate / finalTCTS;
			double finalPriceOrFinalCarat = (finalRapRate * (100 - finalDiscountPercentage)) / 100;
			double finalTotal = total;

			list.get(0).getPktMaster().setFinalTCTS(Double.valueOf(df.format(finalTCTS)));
			list.get(0).getPktMaster().setFinalDiscountPercentage(Double.valueOf(df.format(finalDiscountPercentage)));
			list.get(0).getPktMaster().setFinalRapRate(Double.valueOf(df.format(finalRapRate)));
			list.get(0).getPktMaster().setFinalPriceOrFinalCarat(Double.valueOf(df.format(finalPriceOrFinalCarat)));
			list.get(0).getPktMaster().setFinalTotal(Double.valueOf(df.format(finalTotal)));
			list.get(0).setTotalNoOfRecords(list.size());
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all cart", list, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteCart(String cartId, String path, String ip) {
		Optional<Cart> cart = cartRepository.findById(cartId);
		if (!cart.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Not found in cart.", null,
					HttpStatus.BAD_REQUEST, path);
		User user = cart.get().getUser();
		String stoneId = cart.get().getPktMaster().getStoneId();
		cartRepository.deleteById(cartId);
		userTrackingService.saveTracking(user, ip,
				EnumForUserTracking.STONEID.toString() + stoneId + EnumForUserTracking.STONEDELETEDFROMCART.toString());
		user.getId();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Deleted from cart.", null, HttpStatus.OK, path);
	}

	public void getDiscount(Cart cart) {
		PktMaster responsePktMast = cart.getPktMaster();
		// Set image path for web and app
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast)) {
			ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(responsePktMast.getShape())
					.orElse(new ShapeMaster());
			responsePktMast.setShapeImage(shapeImage.getShapeImage());
		}

		responsePktMast.setDiscOrignal(responsePktMast.getDisc());
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
				&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
						.contains(responsePktMast.getfColor().trim().toLowerCase())) {
			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			responsePktMast.setDisc((double) 0);
		} else {
			/* Add user wise discount */
			disc = discountMasterService.getDiscountByUserId(cart.getUser().getId(), responsePktMast.getGiDate(),
					responsePktMast.getCarat(), responsePktMast.getShape(), false);
			// get default discount
			if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)) {
				disc = discountMasterService.getDiscountByUserId(ShivamWebVariableUtils.DEFAULT_USER_ID,
						responsePktMast.getGiDate(), responsePktMast.getCarat(), responsePktMast.getShape(), true);
			}
			// END default discount get
		}
		Double perCaratDisc = (double) 0;
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			Double perCaratDisFromDisMast = disc.get(0).doubleValue();
			perCaratDisc = responsePktMast.getDisc() + perCaratDisFromDisMast;

		} else if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			Double perCaratDisFromDisMast = disc.get(0).doubleValue();
			perCaratDisc = 0 + perCaratDisFromDisMast;

		} else if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			perCaratDisc = responsePktMast.getDisc() + 0;

		}

		/* .....................End get discount base on userID ................. */
		// getgRapAsRapRate==gRape

		responsePktMast.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));

		// /* End Discount */

		totalWeight = totalWeight + responsePktMast.getCarat();
		totalRapRate = totalRapRate + (responsePktMast.getCarat() * responsePktMast.getgRap());
		responsePktMast.setDisc(perCaratDisc);
		responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
		total = total + (responsePktMast.getCarat() * responsePktMast.getPerCaratePrice()); // weight*percarat
		responsePktMast.setTotalPrice(
				Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
		///

	}

	@Override
	public ResponseWrapperDTO getAllCartByAdmin(String path) {
		// totalWeight = 0;
		// total = 0;
		// totalRapRate = 0;
		List<UserDto> userList = userRepository.getAllUserFromShow();
		System.out.println("userList::" + userList.size());

		List<UserWiseCartDetail> listOfUserWiseCartDetail = new ArrayList<UserWiseCartDetail>();

		

		for (UserDto user : userList) {
			System.err.println("userList::" + user.getEmail());
			List<Cart> list = cartRepository.findAllByUser(user.getId());
			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
				System.err.println("user::" + user.getEmail());
				UserWiseCartDetail userWiseCartDetail = new UserWiseCartDetail();
				userWiseCartDetail.setUser(user);
				userWiseCartDetail.setListOfCart(list);
				listOfUserWiseCartDetail.add(userWiseCartDetail);
			}
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all show user cart", listOfUserWiseCartDetail,
				HttpStatus.OK, path);

	}

	@Override
	public ResponseWrapperDTO updateCartStatus(MyRequestBody body, String cartStatus, HttpServletRequest request) {
		List<Cart> cartList = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<Cart>>() {
				});

		if (ShivamWebMethodUtils.isListNullOrEmpty(cartList))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());

		cartList.forEach(cart -> {
			Optional<Cart> cartDetail = cartRepository.findById(cart.getId());
			if (cartDetail.isPresent()) {
				cartDetail.get().setCartStatus(cartStatus);
				cartRepository.saveAndFlush(cartDetail.get());
			}
		});

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"User Cart " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK,
				request.getServletPath());
	}

}
