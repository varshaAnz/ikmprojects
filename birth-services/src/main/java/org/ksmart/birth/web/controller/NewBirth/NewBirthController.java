package org.ksmart.birth.web.controller.NewBirth;

import lombok.extern.slf4j.Slf4j;

import org.ksmart.birth.birthcommon.model.common.CommonPay;
import org.ksmart.birth.birthcommon.model.common.CommonPayRequest;
import org.ksmart.birth.birthcommon.repoisitory.CommonRepository;
import org.ksmart.birth.birthregistry.model.BirthCertificate;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetailsRequest;
import org.ksmart.birth.birthregistry.service.RegisterBirthService;
import org.ksmart.birth.newbirth.service.NewBirthService;
import org.ksmart.birth.utils.ResponseInfoFactory;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.newbirth.NewBirthApplication;
import org.ksmart.birth.web.model.newbirth.NewBirthDetailRequest;
import org.ksmart.birth.web.model.newbirth.NewBirthResponse;
import org.ksmart.birth.web.model.newbirth.NewBirthSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.ksmart.birth.utils.BirthConstants.STATUS_APPROVED;
import static org.ksmart.birth.utils.BirthConstants.WF_APPROVE;

@Slf4j
@RestController
@RequestMapping("/cr")
public class NewBirthController implements BirthBaseController{
    private final ResponseInfoFactory responseInfoFactory;
    private final NewBirthService ksmartBirthService;
    private final RegisterBirthService registerBirthService;
    private  final CommonRepository repository;
    @Autowired
    NewBirthController(NewBirthService ksmartBirthService, ResponseInfoFactory responseInfoFactory,
                       RegisterBirthService registerBirthService,CommonRepository repository) {
        this.ksmartBirthService=ksmartBirthService;
        this.responseInfoFactory=responseInfoFactory;
        this.registerBirthService = registerBirthService;
        this.repository=repository;
    }

    @PostMapping(value = {"/createbirth"})
    public ResponseEntity<?> saveRegisterBirthDetails(@RequestBody NewBirthDetailRequest request) {
        List<NewBirthApplication> registerBirthDetails=ksmartBirthService.saveKsmartBirthDetails(request);
        NewBirthResponse response= NewBirthResponse.builder()
                                                   .ksmartBirthDetails(registerBirthDetails)
                                                   .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true))
                                                   .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = { "/updatebirth"})
    public ResponseEntity<?> updateRegisterBirthDetails(@RequestBody NewBirthDetailRequest request) {
        BirthCertificate birthCertificate = new BirthCertificate();
        List<NewBirthApplication> birthApplicationDetails=ksmartBirthService.updateBirthDetails(request);
        //Download certificate when Approved
            if ((birthApplicationDetails.get(0).getApplicationStatus().equals(STATUS_APPROVED) && birthApplicationDetails.get(0).getAction().equals(WF_APPROVE))) {
                RegisterBirthDetailsRequest registerBirthDetailsRequest = ksmartBirthService.createRegistryRequest(request);
                if (registerBirthDetailsRequest.getRegisterBirthDetails().size() == 1) {
                    registerBirthService.saveRegisterBirthDetails(registerBirthDetailsRequest);
                }
            }
        NewBirthResponse response=NewBirthResponse.builder()
                .ksmartBirthDetails(birthApplicationDetails)
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),
                        true))
                .birthCertificate(birthCertificate)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = {"/searchbirth"})
    public ResponseEntity<NewBirthSearchResponse> searchKsmartBirth(@RequestBody NewBirthDetailRequest request, @Valid @ModelAttribute SearchCriteria criteria) {
        NewBirthSearchResponse response=ksmartBirthService.searchBirthDetails(request, criteria);
//        NewBirthSearchResponse response=NewBirthSearchResponse.builder()
//                                                              .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), Boolean.TRUE))
//                                                              .newBirthDetails(birthDetails)
//                                                              .count(birthDetails.size())
//                                                              .build();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping(value = {"/searchbirthCommon"})
    public ResponseEntity<NewBirthSearchResponse> searchKsmartBirthCommon(@RequestBody NewBirthDetailRequest request, @Valid @ModelAttribute SearchCriteria criteria) {
        List<NewBirthApplication> birthDetails=ksmartBirthService.searchBirthDetailsCommon(request.getRequestInfo(), criteria);
        NewBirthSearchResponse response=NewBirthSearchResponse.builder()
                                                              .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), Boolean.TRUE))
                                                              .newBirthDetails(birthDetails)
                                                              .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = {"/get"})
    public void getdet(@RequestBody NewBirthDetailRequest request) {
    	
//   	 List<CommonPay> commonPays =  new ArrayList<>();
//	 CommonPay pay = new CommonPay();
//	 
//           
//		  pay.setAction("INITIATE");
//		  pay.setApplicationStatus("INITIATED");
//		  pay.setHasPayment(true);
//		  pay.setAmount(new BigDecimal(10));
//          pay.setIsPaymentSuccess(true);    
//          pay.setApplicationNumber("KL-KOCHI-C-000710-CRBRNR-2023-FILE");
////          pay.setAuditDetails(auditDetails);
//          commonPays.add(pay);
//            
// 
//	 System.out.println("commonPays "+commonPays.get(0).getAction());
//	  
// 	CommonPayRequest paymentReq =CommonPayRequest.builder().requestInfo(request.getRequestInfo())
// 				.commonPays(commonPays).build();
//		
// 	repository.updatePaymentDetails(paymentReq);
    	
//    	String str="new";
//    	char[]  strch = str.toCharArray();
//    	String rev="";
//    	for(int i= str.length()-1;i>=0;i--) {
//    		rev=rev+strch[i];
//    		
//    	}
//    	System.out.println("rev string ="+rev);
    	
//    	int number=1234;
//    	int rev=0;
//    	while(number !=0) {
//    		int remainder=number%10;
//    		rev=rev*10+remainder;
//    		number=number/10;
//    	}
//    	System.out.println("rev num "+rev);
    	
    	
//    	int [] original= {1,2,3,4,5,1};
//    	int [] duplicate = new int [original.length];
//    	for(int i=0; i<=original.length;i++) {
//    		for(int j=i+1;j<original.length;j++) {
//    			if(original[i] == original[j]) {
//    				duplicate[i]=original[j];
//    				System.out.println("dupli  "+ duplicate[i] );
//    				
//    			}
//    		}
//    	}
    List<Integer> yearsList= new ArrayList<Integer>();
    yearsList.add(2021);
    yearsList.add(2025);
    yearsList.add(1988);
    yearsList.add(2000);
    
    System.out.println("sorted list");
    Collections.sort(yearsList,Collections.reverseOrder());
    System.out.println("sorted list op"+yearsList );
    
    	
    	}
}
