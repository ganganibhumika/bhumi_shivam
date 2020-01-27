package com.techhive.shivamweb.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class Test {

	@Autowired
	PktMasterRepository pktMasterRepository;
	static Map<String, Integer> mapForFieldsAndOrder;

	public static void addPKTmasterData() {

		String csvFile = "\\\\NEEL\\backup\\pkt\\pkt.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		// 6/11/2018 9:38:06 AM //csv date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		try {

			br = new BufferedReader(new FileReader(csvFile));

			int lineNumber = 1;

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] pkt = line.split(cvsSplitBy);
				if (lineNumber == 1) {
					mapForFieldsAndOrder = ShivamWebMethodUtils.getFieldsNameAndItsOrderInCSVFile(pkt);
					System.err.println(mapForFieldsAndOrder);
				}

				if (lineNumber > 1) {
					PktMaster pktMaster = new PktMaster();

					pktMaster.setStoneId(pkt[0]);// PId
					System.err.println(pkt[mapForFieldsAndOrder.get("GI_Date")]);
					pktMaster.setSeqNo(Double.valueOf(pkt[3]));// SeqNo
					pktMaster.setCarat(Double.valueOf(pkt[4]));// Carat
					// pktMaster.setGiDate(sdf.parse(pkt[86]));//"GI_Date"
					// pktmaster
					// pktMasterRepository.saveAndFlush(pktMaster);
				}

				lineNumber++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws ParseException {
		String rootPath = System.getProperty("catalina.home")+"webapps/ShivamImage/profilePicture";
		System.err.println(rootPath);
//		C:\Users\neel\AppData\Local\Temp\tomcat.6411635920718745837.1010\webapps\ShivamImage\profilePicture
		File file = new File(rootPath+"imgProfile4028822567587841016758c002b5433e.jpg");
		System.err.println(file.exists());
		// addPKTmasterData();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		String csvFile = "\\\\NEEL\\backup\\pkt\\pkt3.csv";
//		String line = "";
//		String cvsSplitBy = ",";
//		int lineNumber =0;
//		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//
//			while ((line = br.readLine()) != null) {
//				// use comma as separator
//				String[] pkt = line.split(cvsSplitBy);
//				if (lineNumber == 0) {
//					mapForFieldsAndOrder = ShivamWebMethodUtils.getFieldsNameAndItsOrderInCSVFile(pkt);
//					System.err.println(mapForFieldsAndOrder);
//				}
//				if (lineNumber >= 1) {
//					PktMaster pktMaster = new PktMaster();
//					pktMaster.setStoneId(pkt[0]);// PId
//					pktMaster.setSeqNo(Double.valueOf(pkt[1]));// SeqNo
//					pktMaster.setCarat(Double.valueOf(pkt[2]));// Carat
//					 pktMaster.setGiDate(sdf.parse(pkt[3]));//"GI_Date"
//					 pktMaster.setHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("Height")]));
//					 pktMaster.setLength(Double.valueOf(pkt[mapForFieldsAndOrder.get("Length")]));
//					 pktMaster.setWidth(Double.valueOf(pkt[mapForFieldsAndOrder.get("Width")]));
//					 pktMaster.setCertNo(pkt[mapForFieldsAndOrder.get("CertNo")]);
//					 pktMaster.setCodeOfShape(pkt[mapForFieldsAndOrder.get("S_Code")]);
//					 pktMaster.setCodeOfColor(pkt[mapForFieldsAndOrder.get("GC_CODE")]);
//					 pktMaster.setCodeOfClarity(pkt[mapForFieldsAndOrder.get("GQ_CODE")]);
//					 pktMaster.setCodeOfPolish(pkt[mapForFieldsAndOrder.get("GPO_Code")]);
//					 pktMaster.setCodeOfCut(pkt[mapForFieldsAndOrder.get("GCT_CODE")]);
//					 pktMaster.setCodeOfFluorescence(pkt[mapForFieldsAndOrder.get("GFL_Code")]);
//					 pktMaster.setCodeOfSymmentry(pkt[mapForFieldsAndOrder.get("GSY_Code")]);
//					 pktMaster.setCodeOfLuster(pkt[mapForFieldsAndOrder.get("LU_Code")]);
//					 pktMaster.setCodeOfShade(pkt[mapForFieldsAndOrder.get("BSHD_Code")]);
//					 pktMaster.setCodeOfMilky(pkt[mapForFieldsAndOrder.get("MLK_Code")]);
//					 pktMaster.setEyeClean(pkt[mapForFieldsAndOrder.get("EyeClean")]);
//					 pktMaster.setDiameter(Double.valueOf(pkt[mapForFieldsAndOrder.get("Diameter")]));
//					 pktMaster.setTotDepth(Double.valueOf(pkt[mapForFieldsAndOrder.get("TotDepth")]));
//					 pktMaster.setTablePercentage(Double.valueOf(pkt[mapForFieldsAndOrder.get("Table1")]));
//					 pktMaster.setCrownAngle(Double.valueOf(pkt[mapForFieldsAndOrder.get("CAngle")]));
//					 pktMaster.setCrownHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("CHeight")]));
//					 pktMaster.setPavilionAngle(Double.valueOf(pkt[mapForFieldsAndOrder.get("PAngle")]));
//					 pktMaster.setPavilionHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("PHeight")]));
//					 pktMaster.setEfCode(pkt[mapForFieldsAndOrder.get("EF_Code")]);
//					 pktMaster.setDisc(Double.valueOf(pkt[mapForFieldsAndOrder.get("GPer")]));
//					 pktMaster.setgRate(Double.valueOf(pkt[mapForFieldsAndOrder.get("GRate")]));
//					 pktMaster.setgRap(Double.valueOf(pkt[mapForFieldsAndOrder.get("GRap")]));
//					 pktMaster.setLab(pkt[mapForFieldsAndOrder.get("CR_Name")]);
//					 pktMaster.setShape(pkt[mapForFieldsAndOrder.get("S_NAME")]);
//					 pktMaster.setTinName(pkt[mapForFieldsAndOrder.get("TIN_Code")]);
//					 pktMaster.setSinName(pkt[mapForFieldsAndOrder.get("SIN_Code")]);
//					 pktMaster.setToinName(pkt[mapForFieldsAndOrder.get("TOIN_Code")]);
//					 pktMaster.setTbinName(pkt[mapForFieldsAndOrder.get("BIN_Code")]);
//					 pktMaster.setSoinName(pkt[mapForFieldsAndOrder.get("SOIN_Code")]);
//					 pktMaster.setSbinName(pkt[mapForFieldsAndOrder.get("SBIN_Code")]);
//					 pktMaster.setCountry(pkt[mapForFieldsAndOrder.get("Country")]);
//					 pktMaster.setfColor(pkt[mapForFieldsAndOrder.get("fColor")]);
//					 pktMaster.setfIntensity(pkt[mapForFieldsAndOrder.get("fIntensity")]);
//					 pktMaster.setfOvertone(pkt[mapForFieldsAndOrder.get("fOvertone")]);
//					 pktMaster.setStrLn(pkt[mapForFieldsAndOrder.get("StrLn")]);
//					 pktMaster.setLrHalf(pkt[mapForFieldsAndOrder.get("LrHalf")]);
//					 pktMaster.setCodeOfCulet(pkt[mapForFieldsAndOrder.get("CU_Code")]);
//					 pktMaster.setGirdle(Double.valueOf(pkt[mapForFieldsAndOrder.get("Girdle")]));
//					 pktMaster.setIsHold(false);
//					 System.err.println(pktMaster);
////					pktMasterRepository.saveAndFlush(pktMaster);
//					 System.err.println("success");
//				}
//				lineNumber++;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
