package com.kodlamaio.common.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
	public ModelMapper forResponse();
	public ModelMapper forRequest();
}
