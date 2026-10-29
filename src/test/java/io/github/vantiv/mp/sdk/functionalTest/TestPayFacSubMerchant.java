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
    SubMerchantUpdateRequest updateRequest;
    AddressUpdatable address;
    SubMerchantPrimaryContactUpdatable primaryContactUpdatable;
    SubMerchantECheckFeature eCheckFeature;

    ValueAddedServices valueAddedServices;
    ValueAddedServices.Service service;

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
        merchantCategoryCOde = "2";
        bankRoutingNumber = "123";
        bankAccountNumber = "1234";
        pspMerchantId = "112";
        settlementCurrency = "123";

        service = new ValueAddedServices.Service();
        valueAddedServices = new ValueAddedServices();
        service.setCode(ValueAddedServiceProductCode.DISPUTE_DEFENDER);
        service.setEnabled(false);
        valueAddedServices.getServices().add(service);

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
        request.setValueAddedServices(valueAddedServices);

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
        updateRequest.setValueAddedServices(valueAddedServices);

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
