package cuong.run.service.impl;

import org.springframework.stereotype.Service;

import cuong.run.service.InforPageService;
import cuong.run.service.Param;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InforPageServiceImpl implements InforPageService {
	final Param param;
	String name = "";
	int page = 1;

	@Override
	public void setSearch(String name) {
		this.name = param.getString(name, "");
	}

	@Override
	public String getSearch() {
		return this.name;
	}

	@Override
	public void setPage() {
		this.page = param.getInt("page", 1);
	}

	@Override
	public int getPage() {
		return this.page;
	}

}
