package org.ksmart.birth.bornoutside.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.ksmart.birth.utils.BirthConstants;
import org.ksmart.birth.web.model.bornoutside.BornOutsideDetailRequest;
import org.ksmart.birth.web.model.newbirth.NewBirthDetailRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.ksmart.birth.utils.BirthConstants.*;

@Component
@Slf4j
public class BornOutsideMdmsAddressValidation {

    public void validateParentDetails(BornOutsideDetailRequest request, Object mdmsData, Map<String, String> errorMap) {
        List<String> tenantCode = getTenantCodes(mdmsData);

        List<String> stateCodes = getStateCodes(mdmsData);
        List<String> countryCodes = getCountryCodes(mdmsData);
        List<String> talukCodes = getTaulkCodes(mdmsData);
        List<String> villageCodes = getVillageCode(mdmsData);
        List<String> districtCodes = getDistrictCode(mdmsData);
        List<String> postOfficeCodes = getPostOfficeCode(mdmsData);
        List<String> tenantCodes = getTenantCodes(mdmsData);
        request.getNewBirthDetails()
                .forEach(birth -> {
                    if(birth.getParentAddress() != null) {

                        // Country code  Present
                        String countryCodesPresent = birth.getParentAddress().getPresentOutSideCountry();
                        if (log.isDebugEnabled()) {
                            log.debug("Country code Present: \n{}", countryCodesPresent);
                        }
                        if (CollectionUtils.isEmpty(countryCodes) || !countryCodes.contains(countryCodesPresent)) {
                            errorMap.put(COMMON_MDMS_COUNTRY, "The Country code Present'" + countryCodesPresent + "' does not exists");
                        }

                            String countryCodesPremanent = birth.getParentAddress().getPermtaddressCountry();
                            if (log.isDebugEnabled()) {
                                log.debug("Country code Permanent: \n{}", countryCodesPremanent);
                            }
                            if (CollectionUtils.isEmpty(countryCodes) || !countryCodes.contains(countryCodesPremanent)) {
                                errorMap.put(COMMON_MDMS_COUNTRY, "The Country code Permanent'" + countryCodesPremanent + "' does not exists");
                            }


// Permanent Inside kerala

                        if (countryCodesPremanent.contains(COUNTRY_CODE)) {
                            // State code  Permanent
                            String stateCodesPermanent = birth.getParentAddress().getPermtaddressStateName();
                            if (log.isDebugEnabled()) {
                                log.debug("State code Permanent: \n{}", stateCodesPermanent);
                            }
                            if (CollectionUtils.isEmpty(stateCodes) || !stateCodes.contains(stateCodesPermanent)) {
                                errorMap.put(COMMON_MDMS_STATE, "The State code Permanent'" + stateCodesPermanent + "' does not exists");
                            }
                            if (stateCodesPermanent.contains(STATE_CODE_SMALL)) {

                                String tenantCodeCodePermanent = birth.getTenantId();
                                if (log.isDebugEnabled()) {
                                    log.debug("Tenant code : \n{}", tenantCodeCodePermanent);
                                    if (CollectionUtils.isEmpty(tenantCodes) || !tenantCodes.contains(tenantCodeCodePermanent)) {
                                        errorMap.put(CR_MDMS_TENANTS, "The Tenant code  in Permanent Address'" + tenantCodeCodePermanent + "' does not exists");
                                    }
                                }


                                String districtInKeralaCodePermanent = birth.getParentAddress().getPermntInKeralaAdrDistrict();
                                if (log.isDebugEnabled()) {
                                    log.debug("District code : \n{}", districtInKeralaCodePermanent);
                                }
                                if (CollectionUtils.isEmpty(districtCodes) || !districtCodes.contains(districtInKeralaCodePermanent)) {
                                    errorMap.put(COMMON_MDMS_DISTRICT, "The District code  Permanent '" + districtInKeralaCodePermanent + "' does not exists");
                                }

                                String talukCodePermanent = birth.getParentAddress().getPermntInKeralaAdrTaluk();
                                if (log.isDebugEnabled()) {
                                    log.debug("Taulk code Permanent: \n{}", talukCodePermanent);
                                }

                                if (CollectionUtils.isEmpty(talukCodes) || !talukCodes.contains(talukCodePermanent)) {
                                    errorMap.put(COMMON_MDMS_TALUK, "The Taulk code Permanent'" + talukCodePermanent + "' does not exists");
                                }

                                String villageCodePermanent = birth.getParentAddress().getPermntInKeralaAdrVillage();
                                if (log.isDebugEnabled()) {
                                    log.debug("Village code : \n{}", villageCodePermanent);
                                }
                                if (CollectionUtils.isEmpty(villageCodes) || !villageCodes.contains(villageCodePermanent)) {
                                    errorMap.put(COMMON_MDMS_VILLAGE, "The Village code '" + villageCodePermanent + "' does not exists");
                                }

                                String postOfficeCodePermanent = birth.getParentAddress().getPermntInKeralaAdrPostOffice();
                                if (log.isDebugEnabled()) {
                                    log.debug("Postoffice code : \n{}", postOfficeCodePermanent);
                                }

                                if (CollectionUtils.isEmpty(postOfficeCodes) || !postOfficeCodes.contains(postOfficeCodePermanent)) {
                                    errorMap.put(COMMON_MDMS_POSTOFFICE, "The Postoffice code '" + postOfficeCodePermanent + "' does not exists");
                                }

                            } else{  // outside kerala permanent
                                String districtOutKeralaCodePermanent = birth.getParentAddress().getPermntOutsideKeralaDistrict();
                                if (log.isDebugEnabled()) {
                                    log.debug("District code : \n{}", districtOutKeralaCodePermanent);
                                }
                                if (CollectionUtils.isEmpty(districtCodes) || !districtCodes.contains(districtOutKeralaCodePermanent)) {
                                    errorMap.put(COMMON_MDMS_DISTRICT, "The District code '" + districtOutKeralaCodePermanent + "' does not exists");
                                }
                            }
                        }

                    }
                });

    }

    private void validateUnicodeMalayalamAlphabet(String value) {
        if(Character.UnicodeBlock.of('a') == Character.UnicodeBlock.MALAYALAM)
        {
            return;
        }
    }

    private void validateUnicodeEnglishAlphabet(String value) {
        for (char ch : value.toCharArray()) {
            if (Character.UnicodeBlock.of('a') == Character.UnicodeBlock.BASIC_LATIN) {
                return;
            }

        }
    }
    //Tenant
    private List<String> getTenantCodes(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_TENANTS_CODE_JSONPATH);
    }
    private List<String> getTaulkCodes(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_TALUK_CODE_JSONPATH);
    }
    private List<String> getStateCodes(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_STATE_CODE_JSONPATH);
    }

    private List<String> getCountryCodes(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_COUNTRY_CODE_JSONPATH);
    }

    private List<String> getVillageCode(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_VILLAGE_CODE_JSONPATH);
    }

    private List<String> getDistrictCode(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_DISTRICT_CODE_JSONPATH);
    }

    private List<String> getPostOfficeCode(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_POSTOFFICE_CODE_JSONPATH);
    }

    private List<String> getLbTypeCode(Object mdmsData) {
        return JsonPath.read(mdmsData, BirthConstants.CR_MDMS_LBTYPE_CODE_JSONPATH);
    }


}