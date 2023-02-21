package qlnhanvien.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ErrorResponse {

	public ErrorResponse(String message2, String detailMessage, Object object, int code2, String moreInformation2) {
		// TODO Auto-generated constructor stub
	}

	@NonNull
	private String message;
	
	@NonNull
	private String detalMessage;
	
	private Object error;
	
	@NonNull
	private Integer code;
	
	@NonNull
	private String moreInformation;
}
