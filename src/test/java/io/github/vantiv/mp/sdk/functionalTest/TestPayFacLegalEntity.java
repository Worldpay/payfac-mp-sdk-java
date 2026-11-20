package io.github.vantiv.mp.sdk.functionalTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import io.github.vantiv.mp.sdk.Address;
import io.github.vantiv.mp.sdk.AddressUpdatable;
import io.github.vantiv.mp.sdk.Communication;
import io.github.vantiv.mp.sdk.Configuration;
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
    PayFacLegalEntity payFacLegalEntity2;
    Communication communication;
    Properties config;
    Configuration configuration;
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
        communication = new Communication();
        configuration = new Configuration();
        config = configuration.getProperties();
        payFacLegalEntity = new PayFacLegalEntity();
        payFacLegalEntity2 = new PayFacLegalEntity(config);

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

        date = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(date);

        principal = new LegalEntityPrincipal();
        legalEntityPrincipalUpdatable = new LegalEntityPrincipalUpdatable();
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

        updateRequest = new LegalEntityUpdateRequest();
        updateRequest.setAddress(addressUpdatable);
        updateRequest.setContactPhone("77");
        updateRequest.setDoingBusinessAs("Alternate Business Name");
        updateRequest.setAnnualCreditCardSalesVolume(100000000l);
        updateRequest.setHasAcceptedCreditCards(true);
        updateRequest.setPrincipal(legalEntityPrincipalUpdatable);

    }

    @Test
    public void testSetCommunication(){
        payFacLegalEntity.setCommunication(communication);
    }

    @Test
    public void testGetByLegalEntityId(){
        LegalEntityRetrievalResponse response = payFacLegalEntity.getByLegalEntityId(1000293);
        assertNotNull(response.getTransactionId());
        assertEquals("1000293",response.getLegalEntityId());
        assertEquals((short)10,(short)response.getResponseCode());

    }

    @Test
    public void testPostByLegalEntity(){
        LegalEntityCreateResponse response = payFacLegalEntity.postByLegalEntity(createRequest);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getLegalEntityId());
        assertEquals((short)10,(short)response.getResponseCode());
    }

    @Test
    public void testPutByLegalEntity(){
        LegalEntityResponse response = payFacLegalEntity.putByLegalEntity(1000293l,updateRequest);
        assertNotNull(response.getTransactionId());
        assertEquals("1000293",response.getLegalEntityId());
        assertEquals((short)10,(short)response.getResponseCode());
    }

}
