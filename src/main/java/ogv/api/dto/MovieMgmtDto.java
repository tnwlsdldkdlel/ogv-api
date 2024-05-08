package ogv.api.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieMgmtDto {
	
	private Long seq;
	private String name;
	private String director;
	private List<String> actor;
	private String intro;
	private String start;
	private String end;
	private List<Integer> genre;
	private int age;
	private MultipartFile uploadThumbnail;
	private List<String> uploadFileNames;
}
