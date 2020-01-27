package com.techhive.shivamweb.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.PartyMastertRepository;
import com.techhive.shivamweb.response.payload.PartyLoginResponse;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.security.JwtTokenProvider;
import com.techhive.shivamweb.service.PartyMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class PartyMasterServiceImpl implements PartyMasterService {

	@Autowired
	PartyMastertRepository partyMastertRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Override
	public ResponseWrapperDTO savePartyMaster(MyRequestBody body, String path) {
		PartyMaster partyMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), PartyMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(partyMaster.getPartyname(), partyMaster.getPassword()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<PartyMaster> partyName = partyMastertRepository.findBypartyname(partyMaster.getPartyname());

		if (partyName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Party with name '" + partyMaster.getPartyname() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Optional<PartyMaster> partyEmail = partyMastertRepository.findByemail(partyMaster.getEmail());
		if (partyEmail.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Party with email '" + partyMaster.getEmail() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		partyMaster.setPassword(passwordEncoder.encode(partyMaster.getPassword()));
		partyMastertRepository.saveAndFlush(partyMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Party " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO login(String partyName, String password) {

		Optional<PartyMaster> PartyMaster = partyMastertRepository.findBypartynameEmail(partyName);
		if (!PartyMaster.isPresent()) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials", null,
					HttpStatus.UNAUTHORIZED);
		}
		if (PartyMaster.isPresent()
				&& (PartyMaster.get().getPartyname().equalsIgnoreCase(partyName)
						|| PartyMaster.get().getEmail().equalsIgnoreCase(partyName))
				&& passwordEncoder.matches(password, PartyMaster.get().getPassword())
				&& PartyMaster.get().getIsActive() == true) {
			// Authentication authentication = authenticationManager
			// .authenticate(new UsernamePasswordAuthenticationToken(partyName, password));
			//
			// SecurityContextHolder.getContext().setAuthentication(authentication);
			//
			// String jwt = tokenProvider.generateToken(authentication);
			PartyLoginResponse response = new PartyLoginResponse(PartyMaster.get(), null);
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Welcome " + response.getPartyname(), response,
					HttpStatus.OK);
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials", null,
				HttpStatus.UNAUTHORIZED);
	}
}
