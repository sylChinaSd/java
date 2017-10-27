package com.syl.msp.address.dao;

import java.util.List;

import com.syl.msp.address.entity.Address;

public interface AddressDao {

	public List<Address> addressQueryAll(String username) throws Exception;

	public void addressCreate(Address addr) throws Exception;

	public void addressUpdate(Address addr) throws Exception;

	public void addressDelete(Address addr) throws Exception;

	public Address findAddressByIdAndUsername(Address addr) throws Exception;

}
