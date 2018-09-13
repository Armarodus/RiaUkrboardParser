package RiaUkrboardParser.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	private ObjectId id;
	private String ticket;
	private String title;
	private String definitionData;
	private String ticketUrl;
	private String location;
	private String ticketPrice;
	private String ticketPhotoUrl;
}
