package com.syl.msp.concern.dao;

import java.util.List;

import com.syl.msp.concern.entity.Concern;

public interface ConcernDao {

	public Concern findConcern(Concern concern) throws Exception;

	public void delete(Concern result) throws Exception;

	public void save(Concern concern) throws Exception;

	public void deleteByIdAndUsername(Concern c) throws Exception;

	public List<Concern> queryPage(Concern concern) throws Exception;

	public Integer getTotalCount(Concern concern) throws Exception;

}
