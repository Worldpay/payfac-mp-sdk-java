package io.github.vantiv.mp.sdk.functionalTest;

import java.util.Properties;

import io.github.vantiv.mp.sdk.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPayFacSubMerchant {
    PayFacSubMerchant payFacSubMerchant;
    PayFacSubMerchant payFacSubMerchant2;
    Configuration configuration;
    Properties config;
    Communication communication;
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
    SubMerchantCreateRequest.MerchantCategoryTypes categoryType;
    SubMerchantRevenueBoostFeature revenueBoostFeature;
    ComplianceProducts.Product product;
    ComplianceProducts complianceProducts;
    SubMerchantUpdateRequest.MerchantCategoryTypes updateCategoryType;
    SubMerchantUpdateRequest.MethodOfPayments.Method method;
    SubMerchantUpdateRequest.MethodOfPayments methodOfPayments;
    SubMerchantUpdateRequest updateRequest;
    AddressUpdatable address;
    SubMerchantPrimaryContactUpdatable primaryContactUpdatable;
    SubMerchantECheckFeature eCheckFeature;

    @Before
    public void setUp(){
        communication = new Communication();
        configuration = new Configuration();
        config = configuration.getProperties();
        payFacSubMerchant = new PayFacSubMerchant();
        payFacSubMerchant2 = new PayFacSubMerchant(config);
        request = new SubMerchantCreateRequest();
        updateRequest = new SubMerchantUpdateRequest();
        name = "name";
        url = "url";
        customerServiceNumber = "11";
        hardCodedBillingDescriptor = "aaa";
        maxTransactionAmount = 123;
        merchantCategoryCOde = "9222";
        bankRoutingNumber = "123";
        bankAccountNumber = "1234";
        pspMerchantId = "112";
        settlementCurrency = "123";
        categoryType = new SubMerchantCreateRequest.MerchantCategoryTypes();
        categoryType.getCategoryTypes().add("GC");
       // categoryType.getCategoryTypes().add("CLEAR");

        revenueBoostFeature = new SubMerchantRevenueBoostFeature();
        revenueBoostFeature.setEnabled(true);

        product = new ComplianceProducts.Product();
        complianceProducts = new ComplianceProducts();
        product.setCode(ComplianceProductCode.SAFERPAYMENT);
        product.setName("Safer Payment Name");
        product.setActive(true);
        complianceProducts.getProducts().add(product);

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
        method = new SubMerchantUpdateRequest.MethodOfPayments.Method();
        methodOfPayments = new SubMerchantUpdateRequest.MethodOfPayments();
        method.setPaymentType("VISA");
        // method.setAllowedTransactionTypes("NONE");
        method.setSelectedTransactionType("DEPOSITS_ONLY");
        methodOfPayments.getMethods().add(method);

        eCheckFeature = new SubMerchantECheckFeature();
        eCheckFeature.setECheckBillingDescriptor("9785552222");
        updateCategoryType = new SubMerchantUpdateRequest.MerchantCategoryTypes();
        updateCategoryType.getCategoryTypes().add("GC");

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
    public void testSetCommunication(){
        payFacSubMerchant.setCommunication(communication);
    }

    @Test
    public void testGetBySubMerchantId(){
        SubMerchantRetrievalResponse response = payFacSubMerchant.getBySubMerchantId(2018,123456);
        assertNotNull(response.getTransactionId());
        assertEquals("123456",response.getSubMerchantId());
    }

    @Test
    public void testPostSubMerchant(){
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchant(2018,request);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getSubMerchantId());
        assertNull(response.getOriginalSubMerchant());
    }

    @Test
    public void testPostSubMerchantWithDuplicateAll(){
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchantWithDuplicateAll(2018,request);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getSubMerchantId());
        assertNull(response.getOriginalSubMerchant());
    }
    @Test
    public void testPostSubMerchantWithDuplicateNotAll(){
        SubMerchantCreateResponse response = payFacSubMerchant.postSubMerchantWithDuplicateNotAll(2018,request);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getOriginalSubMerchant());
    }

    @Test
    public void testPutBySubMerchantId(){
        Response response = payFacSubMerchant.putBySubMerchantId(2018,123456,updateRequest);
        assertNotNull(response.getTransactionId());
    }
}
