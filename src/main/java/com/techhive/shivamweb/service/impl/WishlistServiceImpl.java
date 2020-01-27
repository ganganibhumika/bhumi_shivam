package com.techhive.shivamweb.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.Cart;
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
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.service.WishlistService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	WishlistRepository wishlistRepository;

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
	public ResponseWrapperDTO saveWishlist(MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		String userId = body.getUserId();
		List<Wishlist> wishlists = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<Wishlist>>() {
				});
		if (ShivamWebMethodUtils.isListNullOrEmpty(wishlists))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);
		StringBuilder result = new StringBuilder();
		Set<String> confirmOrderList = new HashSet<>();
		Set<String> addedToWishList = new HashSet<>();
		Set<String> alreadyInCart = new HashSet<>();
		Set<String> othersConfirm = new HashSet<>();
		Set<String> alreadyInWishlist = new HashSet<>();

		wishlists.forEach(wishlist -> {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(wishlist.getPktMasterId())) {
				Optional<PktMaster> pktMaster = pktMasterRepository.findById(wishlist.getPktMasterId());
				if (pktMaster.isPresent()) {
					if (pktMaster.get().getIsSold() == true) {
						// your confirm order
						if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.get().getConfirmOrder())&&pktMaster.get().getConfirmOrder().getUser().getId().equals(userId)) {
							confirmOrderList.add(pktMaster.get().getStoneId());

						} else
						// other's confirm order add to wishlist
						{
							othersConfirm.add(pktMaster.get().getStoneId());
						}
					} else {
						/// if present in cart than message already in cart
						// Optional<Cart> cartOfUser = cartRepository.findByUserAndPkt(userId,
						// wishlist.getPktMasterId());
						// if (cartOfUser.isPresent()) {
						// alreadyInCart.add(pktMaster.get().getStoneId());
						// } else {
						// Optional<Wishlist> wishlistOfUser =
						// wishlistRepository.findByUserAndPkt(userId,
						// wishlist.getPktMasterId());
						// if (wishlistOfUser.isPresent()) {
						// alreadyInWishlist.add(pktMaster.get().getStoneId());
						// } else {
						// wishlist.setUser(user.get());
						// wishlist.setPktMaster(pktMaster.get());
						// wishlistRepository.saveAndFlush(wishlist);
						// addedToWishList.add(pktMaster.get().getStoneId());
						// }
						// }

						Optional<Wishlist> wishlistOfUser = wishlistRepository.findByUserAndPkt(userId,
								wishlist.getPktMasterId());
						if (wishlistOfUser.isPresent()) {
							alreadyInWishlist.add(pktMaster.get().getStoneId());
						} else {
							wishlist.setUser(user.get());
							wishlist.setPktMaster(pktMaster.get());
							wishlistRepository.saveAndFlush(wishlist);
							addedToWishList.add(pktMaster.get().getStoneId());
						}
						Optional<Cart> cartOfUser = cartRepository.findByUserAndPkt(userId, wishlist.getPktMasterId());
						if (cartOfUser.isPresent()) {
							cartRepository.deleteById(cartOfUser.get().getId());
						}
					}

				}
			}
		});
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(confirmOrderList) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(confirmOrderList.toString())
						+ " already Confirmed.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersConfirm) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersConfirm.toString())
						+ " this stone(s) have been confirmed by other.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(alreadyInWishlist) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(alreadyInWishlist.toString())
						+ " already available in your Wishlist.\n ");
		// result.append(ShivamWebMethodUtils.isSetNullOrEmpty(alreadyInCart) ? ""
		// : ShivamWebMethodUtils.remove1StAndLastCharOfString(alreadyInCart.toString())
		// + " already in your Cart.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(addedToWishList) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToWishList.toString())
						+ " added to Wishlist. ");
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(addedToWishList)) {
			userTrackingService.saveTracking(user.get(), request.getRemoteAddr(),
					EnumForUserTracking.STONEID.toString()
							+ ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToWishList.toString())
							+ EnumForUserTracking.STONEADDEDTOWISHLIST.toString());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, result.toString(), null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllWishlist(String userId, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		List<Wishlist> list = wishlistRepository.findAllByUser(userId);
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
			for (Wishlist Wishlist : list) {
				disc = new ArrayList<>();
				getDiscount(Wishlist);
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

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all wishlist", list, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteWishlist(String wishlistId, String path, String ip) {
		Optional<Wishlist> wishlist = wishlistRepository.findById(wishlistId);
		if (!wishlist.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Not found in Wishlist.", null,
					HttpStatus.BAD_REQUEST, path);
		User user = wishlist.get().getUser();
		String stoneId = wishlist.get().getPktMaster().getStoneId();
		wishlistRepository.deleteById(wishlistId);
		userTrackingService.saveTracking(user, ip, EnumForUserTracking.STONEID.toString() + stoneId
				+ EnumForUserTracking.STONEDELETEDTOWISHLIST.toString());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Deleted from Wishlist.", null, HttpStatus.OK, path);
	}

	public void getDiscount(Wishlist Wishlist) {
		PktMaster responsePktMast = Wishlist.getPktMaster();
		responsePktMast.setDiscOrignal(responsePktMast.getDisc());
		
		// Set image path for web and app
				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast)) {
					ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(responsePktMast.getShape())
							.orElse(new ShapeMaster());
					responsePktMast.setShapeImage(shapeImage.getShapeImage());
				}
				
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
				&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
						.contains(responsePktMast.getfColor().trim().toLowerCase())) {
			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			responsePktMast.setDisc((double) 0);
		} else {
			/* Add user wise discount */
			disc = discountMasterService.getDiscountByUserId(Wishlist.getUser().getId(), responsePktMast.getGiDate(),
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
	public ResponseWrapperDTO deleteMultipleWishlist(MyRequestBody body, String path, String ip) {

		List<Wishlist> wishlists = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<Wishlist>>() {
				});
		Set<String> stoneIds = new HashSet<>();
		User user = new User();
		if (ShivamWebMethodUtils.isListNullOrEmpty(wishlists))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		for (Wishlist wishlist : wishlists) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(wishlist.getId())) {
				Optional<Wishlist> wishlistObj = wishlistRepository.findById(wishlist.getId());
				if (wishlistObj.isPresent()) {
					user = wishlistObj.get().getUser();
					stoneIds.add(wishlistObj.get().getPktMaster().getStoneId());
					wishlistRepository.deleteById(wishlist.getId());

				}
			}
		}
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(user) && !ShivamWebMethodUtils.isSetNullOrEmpty(stoneIds))
			userTrackingService.saveTracking(user, ip,
					EnumForUserTracking.STONEID.toString()
							+ ShivamWebMethodUtils.remove1StAndLastCharOfString(stoneIds.toString())
							+ EnumForUserTracking.STONEDELETEDTOWISHLIST.toString());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Deleted from Wishlist.", null, HttpStatus.OK, path);
	}
}
	
