package com.techhive.shivamweb.response.payload;

import java.util.List;

import com.techhive.shivamweb.master.model.DTO.UserDto;
import com.techhive.shivamweb.model.Cart;

public class UserWiseCartDetail {

	private UserDto user;
	private List<Cart> listOfCart;

		public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<Cart> getListOfCart() {
		return listOfCart;
	}

	public void setListOfCart(List<Cart> listOfCart) {
		this.listOfCart = listOfCart;
	}

}
