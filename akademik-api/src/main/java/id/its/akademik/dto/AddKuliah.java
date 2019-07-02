package id.its.akademik.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class AddKuliah {

	@JsonProperty("frs")
	private List<AddKuliahFRSRequest> frsRequest;

	public AddKuliah() {
	}

	public List<AddKuliahFRSRequest> getFrsRequest() {
		return frsRequest;
	}

	public void setFrsRequest(List<AddKuliahFRSRequest> frsRequest) {
		this.frsRequest = frsRequest;
	}

}
