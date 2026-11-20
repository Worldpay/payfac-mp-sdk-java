package io.github.vantiv.mp.sdk.unitTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

import io.github.vantiv.mp.sdk.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestPayFacSubMerchant {
    PayFacSubMerchant payFacSubMerchant;
    SubMerchantCreateRequest request;
    String name;
    String url;
    String customerServiceNumber;
    String hardCodedBillingDescriptor;
    long maxTransactionAmount;
    String merchantCategoryCOde;
    String bankRoutingNumber;
    String bankAccountNumber;
    String pspMerchantId;
    String settlementCurrency;
    SubMerchantUpdateRequest updateRequest;
    AddressUpdatable address;
    SubMerchantPrimaryContactUpdatable primaryContactUpdatable;
    SubMerchantECheckFeature eCheckFeature;
    SubMerchantCreateRequest.MerchantCategoryTypes categoryType;
    SubMerchantUpdateRequest.MerchantCategoryTypes updateCategoryType;
    SubMerchantUpdateRequest.MethodOfPayments.Method method;
    SubMerchantUpdateRequest.MethodOfPayments methodOfPayments;
    SubMerchantRevenueBoostFeature revenueBoostFeature;
    ComplianceProducts.Product product;
    ComplianceProducts complianceProducts;
    @Before
    public void setUp(){
        payFacSubMerchant = new PayFacSubMerchant();

        name = "name";
        url = "url";
        customerServiceNumber = "11";
        hardCodedBillingDescriptor = "aaa";
        maxTransactionAmount = 123;
        merchantCategoryCOde = "9222";
        bankRoutingNumber = "123";
        bankAccountNumber = "1234";
        pspMerchantId = "112";
        settlementCurrency = "USD";
        categoryType = new SubMerchantCreateRequest.MerchantCategoryTypes();
        categoryType.getCategoryTypes().add("GC");

        revenueBoostFeature = new SubMerchantRevenueBoostFeature();
        revenueBoostFeature.setEnabled(true);

        product = new ComplianceProducts.Product();
        complianceProducts = new ComplianceProducts();
        product.setCode(ComplianceProductCode.SAFERPAYMENT);
        product.setName("Safer Payment Name");
        product.setActive(true);
        complianceProducts.getProducts().add(product);

        request = new SubMerchantCreateRequest();
        request.setMerchantName(name);
        request.setUrl(url);
        request.setCustomerServiceNumber(customerServiceNumber);
        request.setHardCodedBillingDescriptor(hardCodedBillingDescriptor);
        request.setMaxTransactionAmount(maxTransactionAmount);
        request.setMerchantCategoryCode(merchantCategoryCOde);
        request.setBankRoutingNumber(bankRoutingNumber);
        request.setBankAccountNumber(bankAccountNumber);
        request.setPspMerchantId(pspMerchantId);
        request.setSettlementCurrency(settlementCurrency);
        request.setMerchantCategoryTypes(categoryType);
        request.setCountryOfOrigin("CAN");
        request.setRevenueBoost(revenueBoostFeature);
        request.setComplianceProducts(complianceProducts);

        address = new AddressUpdatable();
        address.setStreetAddress1("Street Address 1");
        address.setStreetAddress2("Street Address 2");
        address.setCity("City");
        address.setStateProvince("MA");
        address.setPostalCode("01970");

        primaryContactUpdatable = new SubMerchantPrimaryContactUpdatable();
        primaryContactUpdatable.setFirstName("John");
        primaryContactUpdatable.setLastName("Doe");
        primaryContactUpdatable.setPhone("9785552222");



        eCheckFeature = new SubMerchantECheckFeature();
        eCheckFeature.setECheckBillingDescriptor("9785552222");
        updateCategoryType = new SubMerchantUpdateRequest.MerchantCategoryTypes();
        updateCategoryType.getCategoryTypes().add("GC");
        method = new SubMerchantUpdateRequest.MethodOfPayments.Method();
        methodOfPayments = new SubMerchantUpdateRequest.MethodOfPayments();
        method.setPaymentType("VISA");
        // method.setAllowedTransactionTypes("NONE");
        method.setSelectedTransactionType("NONE");
        methodOfPayments.getMethods().add(method);

        updateRequest = new SubMerchantUpdateRequest();
        updateRequest.setAmexMid("1234567890");
        updateRequest.setDiscoverConveyedMid("123456789012345");
        updateRequest.setUrl("http://merchantUrl");
        updateRequest.setCustomerServiceNumber("8407809000");
        updateRequest.setHardCodedBillingDescriptor("Descriptor");
        updateRequest.setMaxTransactionAmount(8400l);
        updateRequest.setBankRoutingNumber("840123124");
        updateRequest.setBankAccountNumber("84012312415");
        updateRequest.setPspMerchantId("785412365");
        updateRequest.setPurchaseCurrency("USD");
        updateRequest.setAddress(address);
        updateRequest.setPrimaryContact(primaryContactUpdatable);
        updateRequest.setECheck(eCheckFeature);
        updateRequest.setMerchantCategoryTypes(updateCategoryType);
        updateRequest.setMethodOfPayments(methodOfPayments);
        updateRequest.setCountryOfOrigin("CAN");
        updateRequest.setRevenueBoost(revenueBoostFeature);
        updateRequest.setComplianceProducts(complianceProducts);
    }

    @Test
    public void testGetBySubMerchantId(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018/submerchant/123456";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantRetrievalResponse xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "    <merchantName>Merchant Name</merchantName>" +
                "    <amexMid>1234567890</amexMid>" +
                "    <discoverConveyedMid>123456789012345</discoverConveyedMid>" +
                "    <url>http://merchantUrl.com</url>" +
                "    <customerServiceNumber>8407809000</customerServiceNumber>" +
                "    <hardCodedBillingDescriptor>billingDescriptor</hardCodedBillingDescriptor>" +
                "    <maxTransactionAmount>100000</maxTransactionAmount>" +
                "    <purchaseCurrency>USD</purchaseCurrency>" +
                "    <merchantCategoryCode>5964</merchantCategoryCode>" +
                "    <bankRoutingNumber>840123124</bankRoutingNumber>" +
                "    <bankAccountNumber>XXXXX-3124</bankAccountNumber>" +
                "    <pspMerchantId>123456</pspMerchantId>" +
                "    <fraud enabled=\"false\" />" +
                "    <address>" +
                "        <streetAddress1>Street Address 1</streetAddress1>" +
                "        <streetAddress2>Street Address 2</streetAddress2>" +
                "        <city>City</city>" +
                "        <stateProvince>MA</stateProvince>" +
                "        <postalCode>01970</postalCode>" +
                "        <countryCode>USA</countryCode>" +
                "    </address>" +
                "    <primaryContact>" +
                "        <firstName>John</firstName>" +
                "        <lastName>Doe</lastName>" +
                "        <emailAddress>John.Doe@company.com</emailAddress>" +
                "        <phone>9785552222</phone>" +
                "    </primaryContact>" +
                "    <eCheck enabled=\"true\">" +
                "        <eCheckCompanyName>Company Name</eCheckCompanyName>" +
                "        <eCheckBillingDescriptor>9785552222</eCheckBillingDescriptor>" +
                "    </eCheck>" +
                "    <subMerchantFunding enabled=\"true\">" +
                "        <fundingSubmerchantId>12345678901234</fundingSubmerchantId>" +
                "    </subMerchantFunding>" +
                "    <settlementCurrency>USD</settlementCurrency>" +
                "    <subMerchantId>123456</subMerchantId>" +
                "    <amexSellerId>12345678901234</amexSellerId>" +
                "    <disabled>true</disabled>" +
                "    <transactionId>1944734001</transactionId>" +
                "    <merchantIdentString>011000022</merchantIdentString>" +
                "    <credentials>" +
                "        <username>Username</username>" +
                "        <password>Password</password>" +
                "        <passwordExpirationDate>2017-10-03T11:18:23.127-04:00</passwordExpirationDate>" +
                "    </credentials>" +
                "    <paypageCredentials>" +
                "        <paypageCredential>" +
                "            <username>PSPxm1V8</username>" +
                "            <paypageId>Asd23thI974Jpk32</paypageId>" +
                "        </paypageCredential>" +
                "        <paypageCredential>" +
                "            <username>PSPxm1V8Two</username>" +
                "            <paypageId>odzhgcbQX3e3EaKV</paypageId>" +
                "        </paypageCredential>" +
                "        <paypageCredential>" +
                "            <username>PSPxm1V8Three</username>" +
                "            <paypageId>qmnpUBM6G47YJAcq</paypageId>" +
                "        </paypageCredential>" +
                "    </paypageCredentials>" +
                "    <updateDate>2017-09-30T11:18:23.127-04:00</updateDate>" +
                "</subMerchantRetrievalResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
       // when(mockedCommunication.httpGetRequest(matches(expectedRequestUrl))).thenReturn(mockedResponse);
        payFacSubMerchant.setCommunication(mockedCommunication);
        SubMerchantRetrievalResponse response = payFacSubMerchant.getBySubMerchantId(2018,123456);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPostSubMerchant(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018/submerchant";
        String expectedRequest ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantCreateRequest " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<merchantName>name</merchantName>" +
                "<url>url</url>" +
                "<customerServiceNumber>11</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>aaa" +
                "</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>123</maxTransactionAmount>" +
                "<merchantCategoryCode>9222</merchantCategoryCode>" +
                "<bankRoutingNumber>123</bankRoutingNumber>" +
                "<bankAccountNumber>1234</bankAccountNumber>" +
                "<pspMerchantId>112</pspMerchantId>" +
                "<settlementCurrency>USD</settlementCurrency>" +
                "<merchantCategoryTypes>" +
                "<categoryType>GC</categoryType>" +
                "</merchantCategoryTypes>" +
                "<countryOfOrigin>CAN</countryOfOrigin>" +
                "<complianceProducts>"+
                "<product>"+
                "<code>SAFERPAYMENT</code>"+
                "<name>Safer Payment Name</name>"+
                "<active>true</active>"+
                "</product>"+
                "</complianceProducts>"+
                "<sdkVersion>15.0.0</sdkVersion>" +
                "<language>java</language>" +
                "</subMerchantCreateRequest>";
        String mockedResponse = "<subMerchantCreateResponse " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\" duplicate=\"true\">" +
                "<transactionId>82821240469914003</transactionId>" +
                "<originalSubMerchant>" +
                "<merchantName>Merchant Name</merchantName>" +
                "<amexMid>1234567890</amexMid>" +
                "<discoverConveyedMid>123456789012345</discoverConveyedMid>" +
                "<url>http://merchantUrl</url>" +
                "<customerServiceNumber>11</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>aaa</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>123</maxTransactionAmount>" +
                "<merchantCategoryCode>2</merchantCategoryCode>" +
                "<bankRoutingNumber>123</bankRoutingNumber>" +
                "<bankAccountNumber>1234</bankAccountNumber>" +
                "<pspMerchantId>112</pspMerchantId>" +
                "<subMerchantId>1002</subMerchantId>" +
                "<merchantIdentString>01100002</merchantIdentString>" +
                "</originalSubMerchant>" +
                "</subMerchantCreateResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPostRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacSubMerchant.setCommunication(mockedCommunication);
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchant(2018,request);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPostSubMerchantWithDuplicateAll(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018/submerchant";
        String expectedRequest ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantCreateRequest " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<merchantName>duplicate all matching</merchantName>" +
                "<url>url</url>" +
                "<customerServiceNumber>11</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>aaa" +
                "</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>123</maxTransactionAmount>" +
                "<merchantCategoryCode>2</merchantCategoryCode>" +
                "<bankRoutingNumber>123</bankRoutingNumber>" +
                "<bankAccountNumber>1234</bankAccountNumber>" +
                "<pspMerchantId>112</pspMerchantId>" +
                "<settlementCurrency>USD</settlementCurrency>" +
                "<sdkVersion>15.0.0</sdkVersion>" +
                "<language>java</language>" +
                "</subMerchantCreateRequest>";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantCreateResponse " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\" duplicate=\"true\">" +
                "<transactionId>82821240469914003</transactionId>" +
                "<subMerchantId>1100003</subMerchantId>" +
                "<merchantIdentString>01100003</merchantIdentString>" +
                "</subMerchantCreateResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPostRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacSubMerchant.setCommunication(mockedCommunication);
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchantWithDuplicateAll(2018,request);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPostSubMerchantWithDuplicateNotAll(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018/submerchant";
        String expectedRequest ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantCreateRequest " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<merchantName>duplicate not matching</merchantName>" +
                "<url>url</url>" +
                "<customerServiceNumber>11</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>aaa" +
                "</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>123</maxTransactionAmount>" +
                "<merchantCategoryCode>2</merchantCategoryCode>" +
                "<bankRoutingNumber>123</bankRoutingNumber>" +
                "<bankAccountNumber>1234</bankAccountNumber>" +
                "<pspMerchantId>112</pspMerchantId>" +
                "<settlementCurrency>USD</settlementCurrency>" +
                "<sdkVersion>15.0.0</sdkVersion>" +
                "<language>java</language>" +
                "</subMerchantCreateRequest>";
        String mockedResponse = "<subMerchantCreateResponse " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\" duplicate=\"true\">" +
                "<transactionId>82821240469914003</transactionId>" +
                "<originalSubMerchant>" +
                "<merchantName>Merchant Name</merchantName>" +
                "<amexMid>1234567890</amexMid>" +
                "<discoverConveyedMid>123456789012345</discoverConveyedMid>" +
                "<url>url</url>" +
                "<customerServiceNumber>8407809000</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>billing Descriptor</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>8400</maxTransactionAmount>" +
                "<purchaseCurrency>USD</purchaseCurrency>" +
                "<merchantCategoryCode>5964</merchantCategoryCode>" +
                "<bankRoutingNumber>840123124</bankRoutingNumber>" +
                "<bankAccountNumber>84012312415</bankAccountNumber>" +
                "<pspMerchantId>123456</pspMerchantId>" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "<countryCode>USA</countryCode>" +
                "</address>" +
                "<primaryContact>" +
                "<firstName>John</firstName>" +
                "<lastName>Doe</lastName>" +
                "<phone>9785552222</phone>" +
                "<emailAddress>John.Doe@company.com</emailAddress>" +
                "</primaryContact>" +
                "<subMerchantId>1002</subMerchantId>" +
                "<merchantIdentString>01100002</merchantIdentString>" +
                "</originalSubMerchant>" +
                "</subMerchantCreateResponse>";
        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPostRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacSubMerchant.setCommunication(mockedCommunication);
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchantWithDuplicateNotAll(2018,request);
        assertNotNull(response.getTransactionId());
    }

    @Test
    public void testPutBySubMerchantId(){
        String expectedRequestUrl = "https://www.testvantivcnp.com/sandbox/payfac/legalentity/2018/submerchant/123456";
        String expectedRequest ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<subMerchantUpdateRequest " +
                "xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "<amexMid>1234567890</amexMid>" +
                "<discoverConveyedMid>123456789012345</discoverConveyedMid>" +
                "<url>http://merchantUrl</url>" +
                "<customerServiceNumber>8407809000</customerServiceNumber>" +
                "<hardCodedBillingDescriptor>Descriptor</hardCodedBillingDescriptor>" +
                "<maxTransactionAmount>8400</maxTransactionAmount>" +
                "<bankRoutingNumber>840123124</bankRoutingNumber>" +
                "<bankAccountNumber>84012312415</bankAccountNumber>" +
                "<pspMerchantId>785412365</pspMerchantId>" +
                "<purchaseCurrency>USD</purchaseCurrency>" +
                "<address>" +
                "<streetAddress1>Street Address 1</streetAddress1>" +
                "<streetAddress2>Street Address 2</streetAddress2>" +
                "<city>City</city>" +
                "<stateProvince>MA</stateProvince>" +
                "<postalCode>01970</postalCode>" +
                "</address>" +
                "<primaryContact>" +
                "<firstName>John</firstName>" +
                "<lastName>Doe</lastName>" +
                "<phone>9785552222</phone>" +
                "</primaryContact>" +
                "<eCheck>" +
                "<eCheckBillingDescriptor>9785552222</eCheckBillingDescriptor>" +
                "</eCheck>" +
                "<merchantCategoryTypes>" +
                "<categoryType>GC</categoryType>"+
                "</merchantCategoryTypes>"+
                "<methodOfPayments>"+
                "<method>"+
                "<paymentType>VISA</paymentType>"+
                "<selectedTransactionType>NONE</selectedTransactionType>"+
                "</method>"+
                "</methodOfPayments>"+
                "<complianceProducts>"+
                "<product>"+
                    "<code>SAFERPAYMENT</code>"+
                    "<name>Safer Payment Name</name>"+
                    "<active>true</active>"+
                "</product>"+
                "</complianceProducts>"+
                "</subMerchantUpdateRequest>";
        String mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<response xmlns=\"http://payfac.vantivcnp.com/api/merchant/onboard\">" +
                "    <transactionId>8366905079</transactionId>" +
                "</response>";


        Communication mockedCommunication = Mockito.spy(Communication.class);
        when(mockedCommunication.httpPutRequest(expectedRequest, expectedRequestUrl)).thenReturn(mockedResponse);
        payFacSubMerchant.setCommunication(mockedCommunication);

        Response response = payFacSubMerchant.putBySubMerchantId(2018,123456,updateRequest);
        assertNotNull(response.getTransactionId());
    }
}
