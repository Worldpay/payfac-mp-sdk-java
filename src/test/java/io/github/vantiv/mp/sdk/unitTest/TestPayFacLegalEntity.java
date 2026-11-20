package io.github.vantiv.mp.sdk.unitTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.github.vantiv.mp.sdk.Address;
import io.github.vantiv.mp.sdk.AddressUpdatable;
import io.github.vantiv.mp.sdk.Communication;
import io.github.vantiv.mp.sdk.LegalEntityCreateRequest;
import io.github.vantiv.mp.sdk.LegalEntityCreateResponse;
import io.github.vantiv.mp.sdk.LegalEntityOwnershipType;
import io.github.vantiv.mp.sdk.LegalEntityPrincipal;
import io.github.vantiv.mp.sdk.LegalEntityPrincipalUpdatable;
import io.github.vantiv.mp.sdk.LegalEntityResponse;
import io.github.vantiv.mp.sdk.LegalEntityRetrievalResponse;
import io.github.vantiv.mp.sdk.LegalEntityType;
import io.github.vantiv.mp.sdk.LegalEntityUpdateRequest;
import io.github.vantiv.mp.sdk.PayFacLegalEntity;
import io.github.vantiv.mp.sdk.PrincipalAddress;

public class TestPayFacLegalEntity {
    PayFacLegalEntity payFacLegalEntity;
    LegalEntityPrincipal principal;
    LegalEntityPrincipalUpdatable legalEntityPrincipalUpdatable;
    Address address;
    PrincipalAddress principalAddress;
    AddressUpdatable addressUpdatable;
    Date date = new Date();
    Calendar calendar;
    LegalEntityCreateRequest createRequest;
    LegalEntityUpdateRequest updateRequest;


    @Before
    public void setUp(){
        payFacLegalEntity = new PayFacLegalEntity();

        createRequest = new LegalEntityCreateRequest();

        address = new Address();
        principalAddress = new PrincipalAddress();
        addressUpdatable = new AddressUpdatable();
        address.setStreetAddress1("Street Address 1");
        address.setStreetAddress2("Street Address 2");
        address.setCity("City");
        address.setStateProvince("MA");
        address.setPostalCode("01970");
        address.setCountryCode("USA");
        principalAddress.setStreetAddress1("Street Address 1");
        principalAddress.setStreetAddress2("Street Address 2");
        principalAddress.setCity("City");
        principalAddress.setStateProvince("MA");
        principalAddress.setPostalCode("01970");
        principalAddress.setCountryCode("USA");
        addressUpdatable.setStreetAddress1("Street Address 1");
        addressUpdatable.setStreetAddress2("Street Address 2");
        addressUpdatable.setCity("City");
        addressUpdatable.setStateProvince("MA");
        addressUpdatable.setPostalCode("01970");
        addressUpdatable.setCountryCode("USA");

        calendar = Calendar.getInstance();
        calendar.set(1997, 4, 10);

        principal = new LegalEntityPrincipal();
        principal.setTitle("Chief Financial Officer");
        principal.setFirstName("First");
        principal.setLastName("Last");
        principal.setEmailAddress("abc@gmail.com");
        principal.setSsn("123450015");
        principal.setContactPhone("11");
        principal.setDateOfBirth(calendar);
        principal.setDriversLicense("892327409832");
        principal.setDriversLicenseState("MA");
        principal.setAddress(principalAddress);
        principal.setStakePercent(33);

        legalEntityPrincipalUpdatable = new LegalEntityPrincipalUpdatable();
        legalEntityPrincipalUpdatable.setTitle("CEO");
        legalEntityPrincipalUpdatable.setPrincipalId(9);
        legalEntityPrincipalUpdatable.setEmailAddress("abc@gmail.com");
        legalEntityPrincipalUpdatable.setContactPhone("11");
        legalEntityPrincipalUpdatable.setAddress(principalAddress);

        createRequest.setLegalEntityName("LegalEntityCreateRequest request");
        createRequest.setLegalEntityType(LegalEntityType.CORPORATION);
        createRequest.setLegalEntityOwnershipType(LegalEntityOwnershipType.PUBLIC);
        createRequest.setDoingBusinessAs("Alternate Business Name");
        createRequest.setTaxId("123456789");
        createRequest.setContactPhone("77");
        createRequest.setAnnualCreditCardSalesVolume("80000000");
        createRequest.setHasAcceptedCreditCards(true);
        createRequest.setAddress(address);
        createRequest.setPrincipal(principal);
        createRequest.setYearsInBusiness("12");

        updateRequest = new LegalEntityUpdateRequest();
        updateRequest.setAddress(addressUpdatable);
        updateRequest.setContactPhone("77");
        updateRequest.setDoingBusinessAs("Alternate Business Name");
        updateRequest.setAnnualCreditCardSalesVolume(100000000l);
        updateRequest.setHasAcceptedCreditCards(true);
        updateRequest.setPrincipal(legalEntityPrincipalUpdatable);
    }

    @Test
    public void testGetByLegalEntityId(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<legalEntityRetrievalResponse xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\" overallStatus=\"Approved\">" +
                "    <legalEntityName>Legal Entity Name</legalEntityName>" +
                "    <legalEntityType>CORPORATION</legalEntityType>" +
                "    <legalEntityOwnershipType>PUBLIC</legalEntityOwnershipType>" +
                "    <doingBusinessAs>Alternate Name</doingBusinessAs>" +
                "    <taxId>123456789</taxId>" +
                "    <contactPhone>7817659800</contactPhone>" +
                "    <annualCreditCardSalesVolume>80</annualCreditCardSalesVolume>" +
                "    <hasAcceptedCreditCards>true</hasAcceptedCreditCards>" +
                "    <address>" +
                "        <streetAddress1>12 Norton St</streetAddress1>" +
                "        <city>City</city>" +
                "        <stateProvince>NH</stateProvince>" +
                "        <postalCode>03064</postalCode>" +
                "        <countryCode>USA</countryCode>" +
                "    </address>" +
                "    <principal>" +
                "        <principalId>1</principalId>" +
                "        <title>CEO</title>" +
                "        <firstName>p first</firstName>" +
                "        <lastName>p last</lastName>" +
                "        <emailAddress>emailAddress</emailAddress>" +
                "        <ssn>XXXXX-9876</ssn>" +
                "        <contactPhone>7817659800</contactPhone>" +
                "        <dateOfBirth>1980-10-12</dateOfBirth>" +
                "        <driversLicense>XXXXXXXX-9832</driversLicense>" +
                "        <driversLicenseState>MA</driversLicenseState>" +
                "        <address>" +
                "            <streetAddress1>p street address 1</streetAddress1>" +
                "            <streetAddress2>p street address 2</streetAddress2>" +
                "            <city>Boston</city>" +
                "            <stateProvince>MA</stateProvince>" +
                "            <postalCode>01890</postalCode>" +
                "            <countryCode>USA</countryCode>" +
                "        </address>" +
                "        <stakePercent>0</stakePercent>" +
                "    </principal>" +
                "    <legalEntityId>2018</legalEntityId>" +
                "    <responseCode>10</responseCode>" +
                "    <responseDescription>Approved</responseDescription>" +
                "    <backgroundCheckResults>" +
                "        <business>" +
                "            <verificationResult>" +
                "                <overallScore>" +
                "                    <score>10</score>" +
                "                    <description>Significant contradictory findings</description>" +
                "                </overallScore>" +
                "                <nameAddressTaxIdAssociation>" +
                "                    <code>NAME_OR_ADDRESS</code>" +
                "                    <description>The name or the address is verified.</description>" +
                "                </nameAddressTaxIdAssociation>" +
                "                <nameAddressPhoneAssociation>" +
                "                    <code>NAME_ADDRESS_PHONE</code>" +
                "                    <description>Name, address, and phone verified.</description>" +
                "                </nameAddressPhoneAssociation>" +
                "                <verificationIndicators>" +
                "                    <nameVerified>true</nameVerified>" +
                "                    <addressVerified>true</addressVerified>" +
                "                    <cityVerified>true</cityVerified>" +
                "                    <stateVerified>true</stateVerified>" +
                "                    <zipVerified>true</zipVerified>" +
                "                    <phoneVerified>true</phoneVerified>" +
                "                    <taxIdVerified>true</taxIdVerified>" +
                "                </verificationIndicators>" +
                "                <riskIndicators>" +
                "                    <riskIndicator>" +
                "                        <code>UNKNOWN</code>" +
                "                        <description>Supplied information could not be not verified.</description>" +
                "                    </riskIndicator>" +
                "                </riskIndicators>" +
                "            </verificationResult>" +
                "        </business>" +
                "        <principal>" +
                "            <verificationResult>" +
                "                <overallScore>" +
                "                    <score>10</score>" +
                "                    <description>OFAC matches</description>" +
                "                </overallScore>" +
                "                <nameAddressSsnAssociation>" +
                "                    <code>NOTHING</code>" +
                "                    <description>Supplied information could not be not verified.</description>" +
                "                </nameAddressSsnAssociation>" +
                "                <nameAddressPhoneAssociation>" +
                "                    <code>WRONG_PHONE</code>" +
                "                    <description>Supplied Phone number is wrong.</description>" +
                "                </nameAddressPhoneAssociation>" +
                "                <verificationIndicators>" +
                "                    <nameVerified>true</nameVerified>" +
                "                    <addressVerified>true</addressVerified>" +
                "                    <phoneVerified>true</phoneVerified>" +
                "                    <ssnVerified>true</ssnVerified>" +
                "                    <dobVerified>true</dobVerified>" +
                "                </verificationIndicators>" +
                "                <riskIndicators>" +
                "                    <riskIndicator>" +
                "                        <code>UNKNOWN</code>" +
                "                        <description>Supplied information could not be not verified.</description>" +
                "                    </riskIndicator>" +
                "                </riskIndicators>" +
                "            </verificationResult>" +
                "        </principal>" +
                "        <businessToPrincipalAssociation>" +
                "            <score>10</score>" +
                "            <description>Principalâ€™s verified name partially matches input Business name.</description>" +
                "        </businessToPrincipalAssociation>" +
                "        <backgroundCheckDecisionNotes>Additional Info About Decision</backgroundCheckDecisionNotes>" +
                "        <bankruptcyData>" +
                "            <bankruptcyType>none</bankruptcyType>" +
                "            <bankruptcyCount>1</bankruptcyCount>" +
                "            <companyName>Company Name</companyName>" +
                "            <streetAddress1>100 Main Street</streetAddress1>" +
                "            <streetAddress2>Suite 2</streetAddress2>" +
                "            <city>Boston</city>" +
                "            <state>MA</state>" +
                "            <zip>01150</zip>" +
                "            <zip4>2202</zip4>" +
                "            <filingDate>2011-05-13</filingDate>" +
                "        </bankruptcyData>" +
                "        <lienResult>" +
                "            <lienType>Subtype of Lien</lienType>" +
                "            <releasedCount>2</releasedCount>" +
                "            <unreleasedCount>1</unreleasedCount>" +
                "            <companyName>Company Name</companyName>" +
                "            <streetAddress1>100 Main Street</streetAddress1>" +
                "            <streetAddress2>Suite 2</streetAddress2>" +
                "            <city>Boston</city>" +
                "            <state>MA</state>" +
                "            <zip>01150</zip>" +
                "            <zip4>2202</zip4>" +
                "            <filingDate>2011-05-13</filingDate>" +
                "        </lienResult>" +
                "    </backgroundCheckResults>" +
                "    <transactionId>82820200338801030</transactionId>" +
                "    <tinValidationStatus>Approved</tinValidationStatus>" +
                "    <sub_merchant_processing_status>true</sub_merchant_processing_status>" +
                "</legalEntityRetrievalResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        doReturn(mockedResponse).when(mockedCommunication).httpGetRequest(matches(expectedRequestUrl));
        payFacLegalEntity.setCommunication(mockedCommunication);
        LegalEntityRetrievalResponse response = payFacLegalEntity.getByLegalEntityId(2018);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPostByLegalEntity(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity";
        String expectedRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<legalEntityCreateRequest xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<legalEntityName>LegalEntityCreateRequest request</legalEntityName>" +
                "<legalEntityType>CORPORATION</legalEntityType>" +
                "<legalEntityOwnershipType>PUBLIC</legalEntityOwnershipType>" +
                "<doingBusinessAs>Alternate Business Name</doingBusinessAs>" +
                "<taxId>123456789</taxId>" +
                "<contactPhone>77</contactPhone>" +
                "<annualCreditCardSalesVolume>80000000</annualCreditCardSalesVolume>" +
                "<hasAcceptedCreditCards>true</hasAcceptedCreditCards>" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "<countryCode>USA</countryCode>" +
                "</address>" +
                "<principal>" +
                "<title>Chief Financial Officer</title>" +
                "<firstName>First</firstName>" +
                "<lastName>Last</lastName>" +
                "<emailAddress>abc@gmail.com</emailAddress>" +
                "<ssn>123450015</ssn>" +
                "<contactPhone>11</contactPhone>" +
                "<dateOfBirth>1997-05-10</dateOfBirth>" +
                "<driversLicense>892327409832</driversLicense>" +
                "<driversLicenseState>MA</driversLicenseState>" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "<countryCode>USA</countryCode>" +
                "</address>" +
                "<stakePercent>33</stakePercent>" +
                "</principal>" +
                "<yearsInBusiness>12</yearsInBusiness>" +
                "<sdkVersion>15.0.0</sdkVersion>" +
                "<language>java</language>" +
                "</legalEntityCreateRequest>";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<legalEntityCreateResponse xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "    <transactionId>9671076910</transactionId>" +
                "    <legalEntityId>02013</legalEntityId>" +
                "    <responseCode>10</responseCode>" +
                "    <responseDescription>Approved</responseDescription>" +
                "    <backgroundCheckResults>" +
                "        <business>" +
                "            <verificationResult>" +
                "                <overallScore>" +
                "                    <score>40</score>" +
                "                    <description>Business identity is confirmed at the input address</description>" +
                "                </overallScore>" +
                "                <nameAddressTaxIdAssociation>" +
                "                    <code>NAME_ADDRESS_TAX_ID</code>" +
                "                    <description>Name, address, and Tax Id verified.</description>" +
                "                </nameAddressTaxIdAssociation>" +
                "                <nameAddressPhoneAssociation>" +
                "                    <code>NAME_ADDRESS_PHONE</code>" +
                "                    <description>Name, address, and phone verified.</description>" +
                "                </nameAddressPhoneAssociation>" +
                "                <verificationIndicators>" +
                "                    <nameVerified>true</nameVerified>" +
                "                    <addressVerified>true</addressVerified>" +
                "                    <cityVerified>true</cityVerified>" +
                "                    <stateVerified>true</stateVerified>" +
                "                    <zipVerified>true</zipVerified>" +
                "                    <phoneVerified>true</phoneVerified>" +
                "                    <taxIdVerified>true</taxIdVerified>" +
                "                </verificationIndicators>" +
                "                <riskIndicators>" +
                "                    <riskIndicator>" +
                "                        <code>PHONE_NUMBER_MOBILE</code>" +
                "                        <description>The submitted phone number is a mobile number.</description>" +
                "                    </riskIndicator>" +
                "                    <riskIndicator>" +
                "                        <code>PHONE_NUMBER_MOBILE</code>" +
                "                        <description>The submitted phone number is a mobile number.</description>" +
                "                    </riskIndicator>" +
                "                </riskIndicators>" +
                "            </verificationResult>" +
                "        </business>" +
                "        <principal>" +
                "            <verificationResult>" +
                "                <overallScore>" +
                "                    <score>50</score>" +
                "                    <description>Full name, address, phone, and SSN verified.</description>" +
                "                </overallScore>" +
                "                <nameAddressSsnAssociation>" +
                "                    <code>FIRST_LAST_ADDRESS_SSN</code>" +
                "                    <description>First name, last name, address, and SSN verified.</description>" +
                "                </nameAddressSsnAssociation>" +
                "                <nameAddressPhoneAssociation>" +
                "                    <code>LAST_ADDRESS_PHONE</code>" +
                "                    <description>Last name, address, and phone number verified.</description>" +
                "                </nameAddressPhoneAssociation>" +
                "                <verificationIndicators>" +
                "                    <nameVerified>true</nameVerified>" +
                "                    <addressVerified>true</addressVerified>" +
                "                    <phoneVerified>true</phoneVerified>" +
                "                    <ssnVerified>true</ssnVerified>" +
                "                    <dobVerified>true</dobVerified>" +
                "                </verificationIndicators>" +
                "                <riskIndicators>" +
                "                    <riskIndicator>" +
                "                        <code>PHONE_NUMBER_MOBILE</code>" +
                "                        <description>The submitted phone number is a mobile number.</description>" +
                "                    </riskIndicator>" +
                "                    <riskIndicator>" +
                "                        <code>PHONE_NUMBER_MOBILE</code>" +
                "                        <description>The submitted phone number is a mobile number.</description>" +
                "                    </riskIndicator>" +
                "                </riskIndicators>" +
                "            </verificationResult>" +
                "        </principal>" +
                "        <businessToPrincipalAssociation>" +
                "            <score>20</score>" +
                "            <description>Principal’s verified address matches input Business address.</description>" +
                "        </businessToPrincipalAssociation>" +
                "        <backgroundCheckDecisionNotes>2TYmo5BCfiB85KtFY4LT</backgroundCheckDecisionNotes>" +
                "        <bankruptcyData>" +
                "            <bankruptcyType>P81LA</bankruptcyType>" +
                "            <bankruptcyCount>0</bankruptcyCount>" +
                "            <companyName>Company Name</companyName>" +
                "            <streetAddress1>100 Main Street</streetAddress1>" +
                "            <streetAddress2>Suite 2</streetAddress2>" +
                "            <city>Boston</city>" +
                "            <state>MA</state>" +
                "            <zip>01150</zip>" +
                "            <zip4>2202</zip4>" +
                "            <filingDate>2018-07-17</filingDate>" +
                "        </bankruptcyData>" +
                "        <lienResult>" +
                "            <lienType>BKRCIq85xTLZ8mD</lienType>" +
                "            <releasedCount>0</releasedCount>" +
                "            <unreleasedCount>4</unreleasedCount>" +
                "            <companyName>Company Name</companyName>" +
                "            <streetAddress1>100 Main Street</streetAddress1>" +
                "            <streetAddress2>Suite 2</streetAddress2>" +
                "            <city>Boston</city>" +
                "            <state>MA</state>" +
                "            <zip>01150</zip>" +
                "            <zip4>2202</zip4>" +
                "            <filingDate>2018-07-17</filingDate>" +
                "        </lienResult>" +
                "    </backgroundCheckResults>" +
                "    <principal>" +
                "        <principalId>93497</principalId>" +
                "        <firstName>p_first</firstName>" +
                "        <lastName>p_last</lastName>" +
                "    </principal>" +
                "</legalEntityCreateResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPostRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacLegalEntity.setCommunication(mockedCommunication);
        LegalEntityCreateResponse response = payFacLegalEntity.postByLegalEntity(createRequest);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPutByLegalEntity(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/1000293";
        String expectedRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<legalEntityUpdateRequest " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "<countryCode>USA</countryCode>" +
                "</address>" +
                "<contactPhone>77</contactPhone>" +
                "<doingBusinessAs>Alternate Business Name</doingBusinessAs>" +
                "<annualCreditCardSalesVolume>100000000</annualCreditCardSalesVolume>" +
                "<hasAcceptedCreditCards>true</hasAcceptedCreditCards>" +
                "<principal>" +
                "<principalId>9</principalId>" +
                "<title>CEO</title>" +
                "<emailAddress>abc@gmail.com</emailAddress>" +
                "<contactPhone>11</contactPhone>" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "<countryCode>USA</countryCode>" +
                "</address>" +
                "</principal>" +
                "</legalEntityUpdateRequest>";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<legalEntityResponse xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "    <transactionId>0963204802</transactionId>" +
                "    <legalEntityId>2010</legalEntityId>" +
                "    <responseCode>10</responseCode>" +
                "    <responseDescription>Approved</responseDescription>" +
                "</legalEntityResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPutRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacLegalEntity.setCommunication(mockedCommunication);
        LegalEntityResponse response = payFacLegalEntity.putByLegalEntity(1000293l,updateRequest);
        assertNotNull(response.getTransactionId());
    }
}
