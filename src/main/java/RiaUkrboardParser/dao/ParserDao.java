package RiaUkrboardParser.dao;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import RiaUkrboardParser.models.Post;

public interface ParserDao extends CrudRepository<Post, ObjectId>{

}
