package com.epam.newsmanagement.common.dao.utils;

import java.util.List;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;

public class FilterQueryBuilder {

	private static String filterQuery;
	
	private static String filterByAuthorAndTags = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE "
												+ "FROM NEWS N "
												+ "JOIN("
													+ "SELECT NA.NEWS_ID "
													+ "FROM NEWS_AUTHOR NA "
													+ "JOIN NEWS_TAG NT ON NT.NEWS_ID = NA.NEWS_ID "
													+ "WHERE NA.AUTHOR_ID = @AuthorID AND NA.NEWS_ID IN ("
														+ "SELECT DISTINCT NT.NEWS_ID "
														+ "FROM NEWS_TAG NT	"
														+ "WHERE NT.TAG_ID IN (@TagsID)"
													+ ") GROUP BY NA.NEWS_ID) NI "
												+ "ON N.NEWS_ID = NI.NEWS_ID";
	private static String filterByAuthor = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE "
										+ "FROM NEWS N "
										+ "JOIN("
											+ "SELECT NA.NEWS_ID "
											+ "FROM NEWS_AUTHOR NA "
											+ "WHERE NA.AUTHOR_ID = @AuthorID) NI "
										+ "ON N.NEWS_ID = NI.NEWS_ID";
	private static String filterByAuthorForPage = "SELECT QRN.NEWS_ID, QRN.TITLE, QRN.SHORT_TEXT, QRN.FULL_TEXT, QRN.CREATION_DATE, QRN.MODIFICATION_DATE "
												+ "FROM ("
													+ "SELECT QR.*, ROWNUM RN "
													+ "FROM ("
														+ "SELECT N.* "
														+ "FROM NEWS N "
														+ "JOIN ("
															+ "SELECT NA.NEWS_ID "
															+ "FROM NEWS_AUTHOR NA "
															+ "WHERE NA.AUTHOR_ID = @AuthorID) NI "
														+ "ON N.NEWS_ID = NI.NEWS_ID"
													+ ") QR"
												+ ") QRN "
												+ "WHERE QRN.RN BETWEEN @From AND @To";
	private static String filterByAuthorAndTagsForPage = "SELECT QRN.NEWS_ID, QRN.TITLE, QRN.SHORT_TEXT, QRN.FULL_TEXT, QRN.CREATION_DATE, QRN.MODIFICATION_DATE "
														+ "FROM ("
															+ "SELECT QR.*, ROWNUM RN "
															+ "FROM ("
																+ "SELECT N.* "
																+ "FROM NEWS N "
																+ "JOIN ("
																	+ "SELECT NA.NEWS_ID "
																	+ "FROM NEWS_AUTHOR NA "
																	+ "JOIN NEWS_TAG NT ON NT.NEWS_ID = NA.NEWS_ID "
																	+ "WHERE NA.AUTHOR_ID = @AuthorID AND NA.NEWS_ID IN ("
																		+ "SELECT DISTINCT NT.NEWS_ID "
																		+ "FROM NEWS_TAG NT "
																		+ "WHERE NT.TAG_ID IN (@TagsID)"
																	+ ") GROUP BY NA.NEWS_ID) NI "
																+ "ON N.NEWS_ID = NI.NEWS_ID"
															+ ") QR"
														+ ") QRN "
														+ "WHERE QRN.RN BETWEEN @From AND @To";
	private final static String AUTHORID = "@AuthorID";
	private final static String TAGSID = "@TagsID";
	private final static String COMMA = ", ";
	private final static String FROM = "@From";
	private final static String TO = "@To";
	
	public static String buildFilterQuery(SearchParameter searchParameter){
		return build(searchParameter);
	}
	public static String buildFilterQuery(SearchParameter searchParameter, int from, int to){
		return build(searchParameter, from, to);
	}
	
	private static String build(SearchParameter searchParameter){
		if(!searchParameter.getTagList().isEmpty()){
			return buildAuthorAndTagsQuery(searchParameter);
		}else{
			return buildAuthorQuery(searchParameter);
		}
	}
	private static String build(SearchParameter searchParameter, int from, int to){
		if(!searchParameter.getTagList().isEmpty()){
			return buildAuthorAndTagsQuery(searchParameter, from, to);
		}else{
			return buildAuthorQuery(searchParameter, from, to);
		}
	}
	
	private static String buildAuthorAndTagsQuery(SearchParameter searchParameter){
		filterQuery = filterByAuthorAndTags;
		filterQuery = addAuthor(searchParameter.getAuthor());
		filterQuery = addTags(searchParameter.getTagList());
		return filterQuery;
	}
	private static String buildAuthorAndTagsQuery(SearchParameter searchParameter, int from, int to){
		filterQuery = filterByAuthorAndTagsForPage;
		filterQuery = addAuthor(searchParameter.getAuthor());
		filterQuery = addTags(searchParameter.getTagList());
		filterQuery = pagination(from, to);
		return filterQuery;
	}
	
	private static String buildAuthorQuery(SearchParameter searchParameter){
		filterQuery = filterByAuthor;
		filterQuery = addAuthor(searchParameter.getAuthor());
		return filterQuery;
	}
	private static String buildAuthorQuery(SearchParameter searchParameter, int from, int to){
		filterQuery = filterByAuthorForPage;
		filterQuery = addAuthor(searchParameter.getAuthor());
		filterQuery = pagination(from, to);
		return filterQuery;
	}
	
	private static String addAuthor(Author author){
		return filterQuery.replace(AUTHORID, author.getIdAuthor().toString());
	}
	
	private static String addTags(List<Tag> listTags){
		StringBuilder tagsString = new StringBuilder();
		for(int i = 0; i < listTags.size(); i++){
			tagsString.append(listTags.get(i).getIdTag().toString());
			if(i != listTags.size() - 1){
				tagsString.append(COMMA);
			}
		}
		return filterQuery.replace(TAGSID, tagsString);
	}
	
	private static String pagination(int from, int to){
		filterQuery = filterQuery.replace(FROM, String.valueOf(from));
		filterQuery = filterQuery.replaceAll(TO, String.valueOf(to));
		return filterQuery;
	}
}
