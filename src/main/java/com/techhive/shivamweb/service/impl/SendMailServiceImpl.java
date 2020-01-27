package com.techhive.shivamweb.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import com.techhive.shivamweb.exception.RegistrationNotSuccessException;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SendMailServiceImpl implements SendMailService {

	@Autowired
	private UserRepository userRepository;
	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Override
	public void sendMailForRegistrationVerifactrion(User user, String urlForEmailVerification) {

		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Verification for Shivam Jewels");
		emailObj.setText("Click on link to complete Registration");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n" + "\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
						+ user.getFirstName() + " " + user.getLastName() + "</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            Thanks for signing up on www.sj.world. We're excited to have you as an early user.</p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px;text-align: center\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            <a href="
						+ urlForEmailVerification + "/" + user.getId()
						+ " target=\"_blank\" style=\" background: #23295E;\r\n"
						+ "                                                                            color: #ffffff;\r\n"
						+ "                                                                            display: inline-block;\r\n"
						+ "                                                                            font-size: 16px;\r\n"
						+ "                                                                            font-weight: 400;\r\n"
						+ "                                                                            letter-spacing: .3px;\r\n"
						+ "                                                                            padding: 10px 20px;\r\n"
						+ "                                                                            border-radius: 4px;\r\n"
						+ "                                                                            text-decoration: none;\r\n"
						+ "                                                                            text-transform: capitalize;\">Verify your Email</a>\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                               \r\n" + "\r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailToAdmin(User toUser, User From) {

		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(toUser.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("New User Email Confirmation");
		emailObj.setText(From.getUsername() + " confirmed his/her Email");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\" class=\"CToWUd\"></a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi Admin,</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:<b> "
						+ From.getUsername() + "</b> registered in Shivam Jewels, </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                             \r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Please verify this user account.</p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                               \r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr >\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table> \r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForApproved(Optional<User> userFromDb) {

		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(userFromDb.get().getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Account Activation for Shivam Jewels");
		emailObj.setText("Your Account has been activated for Shivam Jewels");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n" + "\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
						+ userFromDb.get().getFirstName() + " " + userFromDb.get().getLastName() + "</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n" + "\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Your Account has been successfully Activated in Shivam\r\n"
						+ "                                                                            Jewels\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n" + "\r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			SendGrid.Response r = sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResponseWrapperDTO sendMailForForgotPassword(String email, String forgotPasswordUrl,
			HttpServletRequest request) {
		try {
			User user = userRepository.findByEmailAndIsDeleted(email, false).orElse(new User());
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getId())) {
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
						null, HttpStatus.BAD_REQUEST, request.getServletPath());
			}
			if (user.getIsEmailVerified() == false)
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
						"Your Email verification pending with us, please verify your Email first.", null,
						HttpStatus.BAD_REQUEST, request.getServletPath());
			user.setPwdRequestDateTime(new Date());

			userRepository.saveAndFlush(user);
			/* send mail */
			SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
			SendGrid.Email emailObj = new SendGrid.Email();
			emailObj.addTo(email);
			emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
			emailObj.setSubject("Shivam Jewels, accept your reset password request.");
			emailObj.setText("Shivam Jewels, accept your request.");
			emailObj.setHtml(
					"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
							+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
							+ "        <tbody>\r\n" + "            <tr>\r\n"
							+ "                <td align=\"center\">\r\n"
							+ "                    <center style=\"width:100%\">\r\n"
							+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
							+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
							+ "                        max-width: 512px;\r\n"
							+ "                        width: inherit;\r\n"
							+ "                        border-radius: 10px;\">\r\n"
							+ "                            <tbody>\r\n" + "                                <tr>\r\n"
							+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
							+ "                                    border-top-right-radius: 10px;\">\r\n"
							+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
							+ "                                            <tbody>\r\n"
							+ "                                                <tr>\r\n"
							+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
							+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
							+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
							+ "                                                                class=\"CToWUd\">\r\n"
							+ "                                                        </a>\r\n"
							+ "                                                    </td>\r\n"
							+ "                                                </tr>\r\n"
							+ "                                            </tbody>\r\n"
							+ "                                        </table>\r\n"
							+ "                                    </td>\r\n"
							+ "                                </tr>\r\n" + "                                <tr>\r\n"
							+ "                                    <td>\r\n"
							+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
							+ "                                            <tbody>\r\n"
							+ "                                                <tr>\r\n"
							+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
							+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
							+ "                                                            <tbody>\r\n" + "\r\n"
							+ "                                                                <tr>\r\n"
							+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
							+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
							+ user.getFirstName() + " " + user.getLastName() + "</h2>\r\n"
							+ "                                                                    </td>\r\n"
							+ "                                                                </tr>\r\n"
							+ "                                                                <tr>\r\n"
							+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
							+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
							+ "                                                                            You have requested a password reset, please follow the link below to reset your password.</p>\r\n"
							+ "                                                                    </td>\r\n"
							+ "                                                                </tr>\r\n"
							+ "                                                                <tr>\r\n"
							+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
							+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
							+ "                                                                            <a href="
							+ forgotPasswordUrl + user.getId() + " target=\"_blank\" style=\" background: #23295E;\r\n"
							+ "                                                                            color: #ffffff;\r\n"
							+ "                                                                            display: inline-block;\r\n"
							+ "                                                                            font-size: 16px;\r\n"
							+ "                                                                            font-weight: 400;\r\n"
							+ "                                                                            letter-spacing: .3px;\r\n"
							+ "                                                                            padding: 10px 20px;\r\n"
							+ "                                                                            border-radius: 4px;\r\n"
							+ "                                                                            text-decoration: none;\r\n"
							+ "                                                                            text-transform: capitalize;\">Reset my password</a>\r\n"
							+ "                                                                        </p>\r\n"
							+ "                                                                    </td>\r\n"
							+ "                                                                </tr>\r\n"
							+ "                                                                <tr>\r\n"
							+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
							+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Please ignore this email if you did not request a\r\n"
							+ "                                                                            password change.\r\n"
							+ "                                                                        </p>\r\n"
							+ "                                                                    </td>\r\n"
							+ "                                                                </tr>\r\n" + "\r\n"
							+ "                                                            </tbody>\r\n"
							+ "                                                        </table>\r\n"
							+ "                                                    </td>\r\n"
							+ "                                                </tr>\r\n"
							+ "                                            </tbody>\r\n"
							+ "                                        </table>\r\n"
							+ "                                    </td>\r\n"
							+ "                                </tr>\r\n" + "\r\n"
							+ "                                <tr>\r\n" + "\r\n"
							+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
							+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
							+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
							+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
							+ "                                    </td>\r\n"
							+ "                                </tr>\r\n" + "                            </tbody>\r\n"
							+ "                        </table>\r\n" + "                    </center>\r\n"
							+ "                </td>\r\n" + "            </tr>\r\n" + "        </tbody>\r\n"
							+ "    </table>\r\n" + "</div>");

			SendGrid.Response response = sendgrid.send(emailObj);
			if (response.getCode() != 200) {
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Error in send mail.", null,
						HttpStatus.BAD_REQUEST, request.getServletPath());
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, ShivamWebVariableUtils.MSG_FOR_RESET_PASSWORD_MAIL,
					null, HttpStatus.OK, request.getServletPath());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RegistrationNotSuccessException(
					"Exception in send mail for forgot pasword,kindly please check your mail or contact to your support team.");

		}
	}

	String finalMiddleHtml = "";

	@Override
	public void sendMailForConfirmOrder(Set<User> users, User from, String confirmStoneId,
			List<ConfirmOrder> confirmSuccess) {
		/* send mail */
		///
		String html = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 950px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                        <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                                <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:\r\n"
				+ "                                                                                        <b> "
				+ from.getUsername() + "</b> has confirmed Stones </p>\r\n"
				+ "                                                                        </td>\r\n"
				+ "                                                                    </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                                With Regards to your confirmation Request posted on our website we hereby Confirm the purchase of the Stones.\r\n"
				+ "                                                                                Please Provide us with your Shipping Address, accordingly our sales person will be update you with the shipping details.\r\n"
				+ "                                                                                Our sales person will get in touch with you at the earliest.</p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\" margin-bottom:20px; color:#262626;font-weight:500;font-size:16px;line-height:1.25\">Your requested stone details are as follows:\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        No of Pieces\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.size() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTCTS() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Rap Disc(%)\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalDiscountPercentage() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Price/cts$\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.get(0).getPktMaster().getFinalPriceOrFinalCarat() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTotal() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                </tr>\r\n"
				+ "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                        <p style=\"text-align: center; margin-top:40px; color:#262626;font-weight:500;font-size:18px;line-height:1.25\">Stone Detail List\r\n"
				+ "                                                                        </p>\r\n" + "\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"3px\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Stone ID\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cert. No.\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Shape\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Clar\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Col\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        RapRate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cut\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Pol\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Sym\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Meas\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Tab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Depth\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Flo\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Lab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Disc\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Rate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Amount\r\n"
				+ "                                                                                    </td></tr>\r\n"
				+ "\r\n";

		String htmlEnd = "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#262626;font-weight:500;font-size:16px;line-height:1.25\">P.S.:\r\n"
				+ "                                                                            <ul>\r\n"
				+ "                                                                                <li> USD 100 will be levied towards shipping and handling charges per invoice amounting below USD 15000.</li>\r\n"
				+ "                                                                                <li>In case you fail to confirm your order within 48 Hrs, we have rights to cancel the order\r\n"
				+ "                                                                                </li>\r\n"
				+ "                                                                            </ul>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thank you so much for doing business with us</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";
		finalMiddleHtml = "";
		confirmSuccess.forEach(confirm -> {

			String htmlMiddle = "";
			htmlMiddle = "                                                                                \r\n"
					+ "                                                                                <tr style=\"text-align: center;\">\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getStoneId())
							? confirm.getPktMaster().getStoneId()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCertNo())
							? confirm.getPktMaster().getCertNo()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getShape())
							? confirm.getPktMaster().getShape()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCarat())
							? confirm.getPktMaster().getCarat()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfClarity())
							? confirm.getPktMaster().getCodeOfClarity()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfColor())
							? confirm.getPktMaster().getCodeOfColor()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getgRap())
							? confirm.getPktMaster().getgRap()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfCut())
							? confirm.getPktMaster().getCodeOfCut()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfPolish())
							? confirm.getPktMaster().getCodeOfPolish()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfSymmentry())
							? confirm.getPktMaster().getCodeOfSymmentry()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getMeasurement())
							? confirm.getPktMaster().getMeasurement()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTablePercentage())
							? confirm.getPktMaster().getTablePercentage()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotDepth())
							? confirm.getPktMaster().getTotDepth()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfFluorescence())
							? confirm.getPktMaster().getCodeOfFluorescence()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getLab())
							? confirm.getPktMaster().getLab()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getDiscount()) ? confirm.getDiscount() : "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getPerCaratePrice())
							? confirm.getPktMaster().getPerCaratePrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotalPrice())
							? confirm.getPktMaster().getTotalPrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "\r\n"
					+ "                                                                                </tr>\r\n";
			finalMiddleHtml = finalMiddleHtml + htmlMiddle;
		});
		///
		users.forEach(user -> {
			SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
			SendGrid.Email emailObj = new SendGrid.Email();
			emailObj.addTo(user.getEmail());
			// emailObj.addBcc(toUsers);
			emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
			emailObj.setSubject("Stone confirmation for Shivam Jewels");
			emailObj.setText("New Stone(s) Confirmed by user");
			emailObj.setHtml(html + finalMiddleHtml.toString() + htmlEnd);
			try {
				sendgrid.send(emailObj);
				// SendGrid.Response r = sendgrid.send(emailObj);
			} catch (SendGridException e) {
				e.printStackTrace();
			}

		});

	}

	@Override
	public ResponseWrapperDTO emailSelectedStone(String email, String fileName, MultipartFile file, String path)
			throws IllegalStateException, IOException {
		File convFile = new File(fileName);
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.flush();
		fos.close();
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(email);
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Shivam Jewels selected stone");
		emailObj.setText("Your selected stone details.");
		emailObj.addAttachment(convFile.getName(), convFile);
		emailObj.setHtml("<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n" + 
				"    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td align=\"center\">\r\n" + 
				"                    <center style=\"width:100%\">\r\n" + 
				"                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n" + 
				"                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n" + 
				"                        max-width: 512px;\r\n" + 
				"                        width: inherit;\r\n" + 
				"                        border-radius: 10px;\">\r\n" + 
				"                            <tbody>\r\n" + 
				"                                <tr>\r\n" + 
				"                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n" + 
				"                                    border-top-right-radius: 10px;\">\r\n" + 
				"                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n" + 
				"                                            <tbody>\r\n" + 
				"                                                <tr>\r\n" + 
				"                                                    <td align=\"center\" valign=\"middle\">\r\n" + 
				"                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n" + 
				"                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n" + 
				"                                                                class=\"CToWUd\">\r\n" + 
				"                                                        </a>\r\n" + 
				"                                                    </td>\r\n" + 
				"                                                </tr>\r\n" + 
				"                                            </tbody>\r\n" + 
				"                                        </table>\r\n" + 
				"                                    </td>\r\n" + 
				"                                </tr>\r\n" + 
				"                                <tr>\r\n" + 
				"                                    <td>\r\n" + 
				"                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n" + 
				"                                            <tbody>\r\n" + 
				"                                                <tr>\r\n" + 
				"                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n" + 
				"                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n" + 
				"                                                            <tbody>\r\n" + 
				"                                                                <tr>\r\n" + 
				"                                                                    <td style=\"padding-bottom:20px\">\r\n" + 
				"                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi,</h2>\r\n" + 
				"                                                                    </td>\r\n" + 
				"                                                                </tr>\r\n" + 
				"                                                                <tr>\r\n" + 
				"                                                                    <td style=\"padding-bottom:20px\">\r\n" + 
				"                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">We have generated file of Stone List as per your request.\r\n" + 
				"                                                                            Please find Stone list file attchment. \r\n" + 
				"                                                                        </p>\r\n" + 
				"                                                                    </td>\r\n" + 
				"                                                                </tr>\r\n" + 
				"                                                                \r\n" + 
				"                                                                \r\n" + 
				"                                                            </tbody>\r\n" + 
				"                                                        </table>\r\n" + 
				"                                                    </td>\r\n" + 
				"                                                </tr>\r\n" + 
				"                                            </tbody>\r\n" + 
				"                                        </table>\r\n" + 
				"                                    </td>\r\n" + 
				"                                </tr>\r\n" + 
				"\r\n" + 
				"                                <tr>\r\n" + 
				"\r\n" + 
				"                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n" + 
				"                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n" + 
				"                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n" + 
				"                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n" + 
				"                                    </td>\r\n" + 
				"                                </tr>\r\n" + 
				"                            </tbody>\r\n" + 
				"                        </table>\r\n" + 
				"                    </center>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>");
		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
		convFile.delete();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Email with generated Excel sent successfully.", null,
				HttpStatus.OK, path);
	}

	@Override
	public void sendMailForViewRequest(User user, Date startDate, Date endDate, String stoneId) {
		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Your stone view request for Shivam Jewels");
		emailObj.setText("Your stone view request for Shivam Jewels");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
						+ user.getFirstName() + " " + user.getLastName() + ",</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">You have requested for Viewing the Stone.\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            Stone View request list are as follows:\r\n"
						+ "                                                                            <b>" + stoneId
						+ "</b>\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            Stone View Request Date&Time: "
						+ sf.format(startDate) + " to " + sf.format(endDate) + " </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            You must be complete Stone View Request Process within 2 hour as per given time.\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n" + "\r\n" + "\r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForDemand(User toUser, User demandFrom, String shape) {
		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(toUser.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("New Stone Demand");
		emailObj.setText("New Stone demand from user.");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\" class=\"CToWUd\"></a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:<b>"
						+ demandFrom.getUsername() + "</b> has demand for " + shape + " shape Stones. </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                               \r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr >\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table> \r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForFeedback(User toUser, User user) {
		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(toUser.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("New Suggestions & Feedback.");
		emailObj.setText("New Suggestions & Feedback from user.");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:\r\n"
						+ "                                                                            <b> "
						+ user.getUsername() + "</b> has sent Suggestions & Feedback. </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                \r\n" + "\r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForOfferDiscount(User toUser, User from, String offerStone) {
		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(toUser.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("New Offer request for stone.");
		emailObj.setText("New stone(s) Offer request from user.");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:\r\n"
						+ "                                                                            <b> "
						+ from.getUsername() + "</b> has placed offer for Stones: </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            <b>" + offerStone
						+ "</b>\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n" + "\r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForFullfilDemand(User user, String shape) {
		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("New stone matching your demand arrived.");
		emailObj.setText("New stone matching your demand has arrived in our site.");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\" class=\"CToWUd\"></a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
						+ user.getFirstName() + " " + user.getLastName() + ",</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">You have placed demand for "
						+ shape + " Shape Stones.\r\n"
						+ "                                                                                Now you can get your demanded Shape stones in our site.</p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                               \r\n"
						+ "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr >\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table> \r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailForConfirmOrderToUser(User user, User emailUser, String remove1StAndLastCharOfString,
			List<ConfirmOrder> confirmSuccess) {
		/* send mail */
		///
		String html = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 950px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
				+ user.getFirstName() + " " + user.getLastName() + "</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                                With Regards to your confirmation Request posted on our website we hereby Confirm the purchase of the Stones.\r\n"
				+ "                                                                                Please Provide us with your Shipping Address, accordingly our sales person will be update you with the shipping details.\r\n"
				+ "                                                                                Our sales person will get in touch with you at the earliest.</p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\" margin-bottom:20px; color:#262626;font-weight:500;font-size:16px;line-height:1.25\">Your requested stone details are as follows:\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        No of Pieces\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.size() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTCTS() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Rap Disc(%)\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalDiscountPercentage() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Price/cts$\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.get(0).getPktMaster().getFinalPriceOrFinalCarat() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTotal() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                </tr>\r\n"
				+ "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                        <p style=\"text-align: center; margin-top:40px; color:#262626;font-weight:500;font-size:18px;line-height:1.25\">Stone Detail List\r\n"
				+ "                                                                        </p>\r\n" + "\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"3px\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Stone ID\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cert. No.\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Shape\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Clar\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Col\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        RapRate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cut\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Pol\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Sym\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Meas\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Tab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Depth\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Flo\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Lab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Disc\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Rate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Amount\r\n"
				+ "                                                                                    </td></tr>\r\n"
				+ "\r\n";

		String htmlEnd = "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#262626;font-weight:500;font-size:16px;line-height:1.25\">P.S.:\r\n"
				+ "                                                                            <ul>\r\n"
				+ "                                                                                <li> USD 100 will be levied towards shipping and handling charges per invoice amounting below USD 15000.</li>\r\n"
				+ "                                                                                <li>In case you fail to confirm your order within 48 Hrs, we have rights to cancel the order\r\n"
				+ "                                                                                </li>\r\n"
				+ "                                                                            </ul>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thank you so much for doing business with us</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";
		finalMiddleHtml = "";
		confirmSuccess.forEach(confirm -> {

			String htmlMiddle = "";
			htmlMiddle = "                                                                                \r\n"
					+ "                                                                                <tr style=\"text-align: center;\">\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getStoneId())
							? confirm.getPktMaster().getStoneId()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCertNo())
							? confirm.getPktMaster().getCertNo()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getShape())
							? confirm.getPktMaster().getShape()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCarat())
							? confirm.getPktMaster().getCarat()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfClarity())
							? confirm.getPktMaster().getCodeOfClarity()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfColor())
							? confirm.getPktMaster().getCodeOfColor()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getgRap())
							? confirm.getPktMaster().getgRap()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfCut())
							? confirm.getPktMaster().getCodeOfCut()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfPolish())
							? confirm.getPktMaster().getCodeOfPolish()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfSymmentry())
							? confirm.getPktMaster().getCodeOfSymmentry()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getMeasurement())
							? confirm.getPktMaster().getMeasurement()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTablePercentage())
							? confirm.getPktMaster().getTablePercentage()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotDepth())
							? confirm.getPktMaster().getTotDepth()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfFluorescence())
							? confirm.getPktMaster().getCodeOfFluorescence()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getLab())
							? confirm.getPktMaster().getLab()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getDiscount()) ? confirm.getDiscount() : "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getPerCaratePrice())
							? confirm.getPktMaster().getPerCaratePrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotalPrice())
							? confirm.getPktMaster().getTotalPrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "\r\n"
					+ "                                                                                </tr>\r\n";
			finalMiddleHtml = finalMiddleHtml + htmlMiddle;
		});
		///
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		// emailObj.addTo("prakashpambhar04@gmail.com");
		// emailObj.addBcc(toUsers);
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Your stone confirmation for Shivam Jewels");
		emailObj.setText("You have successfully confirmed stone(s) for Shivam Jewels");
		emailObj.setHtml(html + finalMiddleHtml.toString() + htmlEnd);
		try {
			sendgrid.send(emailObj);
			// SendGrid.Response r = sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMailToUserOfSuccess(User user) {

		/* send mail */
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Email successfully verified for Shivam Jewels");
		emailObj.setText("Your email veified successfully with us.");
		emailObj.setHtml(
				"<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
						+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
						+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
						+ "                    <center style=\"width:100%\">\r\n"
						+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
						+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
						+ "                        max-width: 512px;\r\n"
						+ "                        width: inherit;\r\n"
						+ "                        border-radius: 10px;\">\r\n"
						+ "                            <tbody>\r\n" + "                                <tr>\r\n"
						+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
						+ "                                    border-top-right-radius: 10px;\">\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
						+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
						+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
						+ "                                                                class=\"CToWUd\">\r\n"
						+ "                                                        </a>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                                <tr>\r\n" + "                                    <td>\r\n"
						+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                            <tbody>\r\n"
						+ "                                                <tr>\r\n"
						+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
						+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
						+ "                                                            <tbody>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Congratulation, You have successfully registered\r\n"
						+ "                                                                            on\r\n"
						+ "                                                                            <a>www.sj.world</a>\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Dear "
						+ user.getFirstName() + " " + user.getLastName() + ",</h2>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
						+ "                                                                            Thank you for registering with www.sj.world We have received your registration details and is currently under the\r\n"
						+ "                                                                            review process. Once we verify these details\r\n"
						+ "                                                                            you will receive another e-mail when your account\r\n"
						+ "                                                                            is approved. </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>\r\n"
						+ "                                                                <tr>\r\n"
						+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
						+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">In the meantime, we would love it if you could spread\r\n"
						+ "                                                                            the word about us with your friends. Please drop\r\n"
						+ "                                                                            us an email in case you have any questions. Thank\r\n"
						+ "                                                                            you for your patience.\r\n"
						+ "                                                                        </p>\r\n"
						+ "                                                                    </td>\r\n"
						+ "                                                                </tr>                                                    \r\n"
						+ "\r\n" + "                                                            </tbody>\r\n"
						+ "                                                        </table>\r\n"
						+ "                                                    </td>\r\n"
						+ "                                                </tr>\r\n"
						+ "                                            </tbody>\r\n"
						+ "                                        </table>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "\r\n" + "                                <tr>\r\n" + "\r\n"
						+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
						+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
						+ "                                    </td>\r\n" + "                                </tr>\r\n"
						+ "                            </tbody>\r\n" + "                        </table>\r\n"
						+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>");

		try {
			sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMailForConfirmOffer(Set<User> users, User from, String remove1StAndLastCharOfString,
			List<ConfirmOrder> confirmSuccess) {
		/* send mail */
		///
		String html = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 950px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Stone Offer was placed by\r\n"
				+ "                                                                            <b> "
				+ from.getUsername() + "</b>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            With Regards to your Discount Request posted on our website we hereby Confirm the purchase of the Stones. Please Provide\r\n"
				+ "                                                                            us with your Shipping Address, accordingly our\r\n"
				+ "                                                                            sales person will be update you with the shipping\r\n"
				+ "                                                                            details. Our sales person will get in touch with\r\n"
				+ "                                                                            you at the earliest.</p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\" margin-bottom:20px; color:#262626;font-weight:500;font-size:16px;line-height:1.25\">Your requested stone details are as follows:\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        No of Pieces\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.size() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTCTS() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Rap Disc(%)\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalDiscountPercentage() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Price/cts$\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.get(0).getPktMaster().getFinalPriceOrFinalCarat() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTotal() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                </tr>\r\n"
				+ "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                        <p style=\"text-align: center; margin-top:40px; color:#262626;font-weight:500;font-size:18px;line-height:1.25\">Stone Detail List\r\n"
				+ "                                                                        </p>\r\n" + "\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"3px\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Stone ID\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cert. No.\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Shape\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Clar\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Col\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        RapRate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cut\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Pol\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Sym\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Meas\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Tab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Depth\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Flo\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Lab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Disc\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Rate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Amount\r\n"
				+ "                                                                                    </td></tr>\r\n"
				+ "\r\n";

		String htmlEnd = "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#262626;font-weight:500;font-size:16px;line-height:1.25\">P.S.:\r\n"
				+ "                                                                            <ul>\r\n"
				+ "                                                                                <li> USD 100 will be levied towards shipping and handling charges per invoice amounting below USD 15000.</li>\r\n"
				+ "                                                                                <li>In case you fail to confirm your order within 48 Hrs, we have rights to cancel the order\r\n"
				+ "                                                                                </li>\r\n"
				+ "                                                                            </ul>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thank you so much for doing business with us</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";
		finalMiddleHtml = "";
		confirmSuccess.forEach(confirm -> {

			String htmlMiddle = "";
			htmlMiddle = "                                                                                \r\n"
					+ "                                                                                <tr style=\"text-align: center;\">\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getStoneId())
							? confirm.getPktMaster().getStoneId()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCertNo())
							? confirm.getPktMaster().getCertNo()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getShape())
							? confirm.getPktMaster().getShape()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCarat())
							? confirm.getPktMaster().getCarat()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfClarity())
							? confirm.getPktMaster().getCodeOfClarity()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfColor())
							? confirm.getPktMaster().getCodeOfColor()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getgRap())
							? confirm.getPktMaster().getgRap()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfCut())
							? confirm.getPktMaster().getCodeOfCut()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfPolish())
							? confirm.getPktMaster().getCodeOfPolish()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfSymmentry())
							? confirm.getPktMaster().getCodeOfSymmentry()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getMeasurement())
							? confirm.getPktMaster().getMeasurement()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTablePercentage())
							? confirm.getPktMaster().getTablePercentage()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotDepth())
							? confirm.getPktMaster().getTotDepth()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfFluorescence())
							? confirm.getPktMaster().getCodeOfFluorescence()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getLab())
							? confirm.getPktMaster().getLab()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getDiscount()) ? confirm.getDiscount() : "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getPerCaratePrice())
							? confirm.getPktMaster().getPerCaratePrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotalPrice())
							? confirm.getPktMaster().getTotalPrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "\r\n"
					+ "                                                                                </tr>\r\n";
			finalMiddleHtml = finalMiddleHtml + htmlMiddle;
		});
		///
		users.forEach(user -> {
			SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
			SendGrid.Email emailObj = new SendGrid.Email();
			emailObj.addTo(user.getEmail());
			// emailObj.addBcc(toUsers);
			emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
			emailObj.setSubject("Stone Offer confirmation for Shivam Jewels");
			emailObj.setText("New Stone(s) Offer confirmed of user.");
			emailObj.setHtml(html + finalMiddleHtml + htmlEnd);
			try {
				sendgrid.send(emailObj);
				// SendGrid.Response r = sendgrid.send(emailObj);
			} catch (SendGridException e) {
				e.printStackTrace();
			}

		});

	}

	@Override
	public void sendMailForConfirmOfferToUser(User user, User emailUserz, String remove1StAndLastCharOfString,
			List<ConfirmOrder> confirmSuccess) {
		/* send mail */
		///
		String html = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 950px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, "
				+ user.getFirstName() + " " + user.getLastName() + ",</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">With Regards to your Discount Request posted on our\r\n"
				+ "                                                                            website we hereby Confirm the purchase of the\r\n"
				+ "                                                                            Stones. Please Provide us with your Shipping\r\n"
				+ "                                                                            Address, accordingly our sales person will be\r\n"
				+ "                                                                            update you with the shipping details. Our sales\r\n"
				+ "                                                                            person will get in touch with you at the earliest.\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\" margin-bottom:20px; color:#262626;font-weight:500;font-size:16px;line-height:1.25\">Your requested stone details are as follows:\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        No of Pieces\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.size() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTCTS() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Rap Disc(%)\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalDiscountPercentage() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Price/cts$\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                       "
				+ confirmSuccess.get(0).getPktMaster().getFinalPriceOrFinalCarat() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"background: #23295E;color:#ffffff;padding: 5px;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        Total\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #23295E;color: #23295E;padding: 5px;font-size:14px;\">\r\n"
				+ "                                                                                        "
				+ confirmSuccess.get(0).getPktMaster().getFinalTotal() + "\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                </tr>\r\n"
				+ "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                        <p style=\"text-align: center; margin-top:40px; color:#262626;font-weight:500;font-size:18px;line-height:1.25\">Stone Detail List\r\n"
				+ "                                                                        </p>\r\n" + "\r\n"
				+ "                                                                        <table border=\"0\" cellspacing=\"3px\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                                            <tbody>\r\n" + "\r\n"
				+ "                                                                                <tr style=\"text-align: center;\">\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Stone ID\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cert. No.\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Shape\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Carat\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Clar\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Col\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        RapRate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Cut\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Pol\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Sym\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Meas\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Tab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Depth\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Flo\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Lab\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Disc\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Rate\r\n"
				+ "                                                                                    </td>\r\n"
				+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 600; padding: 5px; font-size:12px;\">\r\n"
				+ "                                                                                        Amount\r\n"
				+ "                                                                                    </td></tr>\r\n"
				+ "\r\n";

		String htmlEnd = "                                                                            </tbody>\r\n"
				+ "                                                                        </table>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#262626;font-weight:500;font-size:16px;line-height:1.25\">P.S.:\r\n"
				+ "                                                                            <ul>\r\n"
				+ "                                                                                <li> USD 100 will be levied towards shipping and handling charges per invoice amounting below USD 15000.</li>\r\n"
				+ "                                                                                <li>In case you fail to confirm your order within 48 Hrs, we have rights to cancel the order\r\n"
				+ "                                                                                </li>\r\n"
				+ "                                                                            </ul>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thank you so much for doing business with us</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";
		finalMiddleHtml = "";
		confirmSuccess.forEach(confirm -> {

			String htmlMiddle = "";
			htmlMiddle = "                                                                                \r\n"
					+ "                                                                                <tr style=\"text-align: center;\">\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getStoneId())
							? confirm.getPktMaster().getStoneId()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCertNo())
							? confirm.getPktMaster().getCertNo()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getShape())
							? confirm.getPktMaster().getShape()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCarat())
							? confirm.getPktMaster().getCarat()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                      "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfClarity())
							? confirm.getPktMaster().getCodeOfClarity()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfColor())
							? confirm.getPktMaster().getCodeOfColor()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getgRap())
							? confirm.getPktMaster().getgRap()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfCut())
							? confirm.getPktMaster().getCodeOfCut()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfPolish())
							? confirm.getPktMaster().getCodeOfPolish()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfSymmentry())
							? confirm.getPktMaster().getCodeOfSymmentry()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getMeasurement())
							? confirm.getPktMaster().getMeasurement()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTablePercentage())
							? confirm.getPktMaster().getTablePercentage()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotDepth())
							? confirm.getPktMaster().getTotDepth()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getCodeOfFluorescence())
							? confirm.getPktMaster().getCodeOfFluorescence()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getLab())
							? confirm.getPktMaster().getLab()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getDiscount()) ? confirm.getDiscount() : "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                       "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getPerCaratePrice())
							? confirm.getPktMaster().getPerCaratePrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "                                                                                    <td style=\"border:1px solid #d7d7d7;color: #262626;font-weight: 400; padding: 5px; font-size:12px;\">\r\n"
					+ "                                                                                        "
					+ (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirm.getPktMaster().getTotalPrice())
							? confirm.getPktMaster().getTotalPrice()
							: "-")
					+ "\r\n"
					+ "                                                                                    </td>\r\n"
					+ "\r\n"
					+ "                                                                                </tr>\r\n";
			finalMiddleHtml = finalMiddleHtml + htmlMiddle;
		});
		///
		SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
		SendGrid.Email emailObj = new SendGrid.Email();
		emailObj.addTo(user.getEmail());
		// emailObj.addBcc(toUsers);
		emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
		emailObj.setSubject("Your stone Offer confirmation for Shivam Jewels");
		emailObj.setText("You stone Offer is successfully confirmed for Shivam Jewels");
		emailObj.setHtml(html + finalMiddleHtml + htmlEnd);
		try {
			sendgrid.send(emailObj);
			// SendGrid.Response r = sendgrid.send(emailObj);
		} catch (SendGridException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMailForViewRequestToAdmin(Set<User> admins, User ofUser, Date from, Date toTime, String stoneList) {
		/* send mail */
		///
		String html = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 512px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, Admin,</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">UserName:\r\n"
				+ "                                                                            <b> "
				+ ofUser.getUsername() + "</b> has requested for Viewing the Stone.\r\n"
				+ "                                                                            </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            Stone View request list are as follows:\r\n"
				+ "                                                                            <b>" + stoneList
				+ "</b>\r\n" + "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            Stone View Request Date&Time: "
				+ sf.format(from) + " to " + sf.format(toTime) + " </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            You must be complete Stone View Request Process within 2 hour as per given time.\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";

		///
		admins.forEach(user -> {
			SendGrid sendgrid = new SendGrid(ShivamWebVariableUtils.API_KEY_FOR_SENDGRID);
			SendGrid.Email emailObj = new SendGrid.Email();
			emailObj.addTo(user.getEmail());
			// emailObj.addBcc(toUsers);
			emailObj.setFrom(ShivamWebVariableUtils.SENDER_EMAIL_FOR_SEND_MAIL);
			emailObj.setSubject("Stone View request for Shivam Jewels");
			emailObj.setText("New Stone(s) View request of user.");
			emailObj.setHtml(html);
			try {
				sendgrid.send(emailObj);
				// SendGrid.Response r = sendgrid.send(emailObj);
			} catch (SendGridException e) {
				e.printStackTrace();
			}

		});

	}

}
