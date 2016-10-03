package com.epam.newsmanagement.common.dao.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;

public class FilterQueryBuilderTest {
	
	private static String resultForFilterByAuthorAndTags = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE "
			+ "FROM NEWS N "
			+ "JOIN("
				+ "SELECT NA.NEWS_ID "
				+ "FROM NEWS_AUTHOR NA "
				+ "JOIN NEWS_TAG NT ON NT.NEWS_ID = NA.NEWS_ID "
				+ "WHERE NA.AUTHOR_ID = 1 AND NA.NEWS_ID IN ("
					+ "SELECT DISTINCT NT.NEWS_ID "
					+ "FROM NEWS_TAG NT	"
					+ "WHERE NT.TAG_ID IN (1, 2, 3)"
				+ ") GROUP BY NA.NEWS_ID) NI "
			+ "ON N.NEWS_ID = NI.NEWS_ID";
private static String resultForFilterByAuthor = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE "
	+ "FROM NEWS N "
	+ "JOIN("
		+ "SELECT NA.NEWS_ID "
		+ "FROM NEWS_AUTHOR NA "
		+ "WHERE NA.AUTHOR_ID = 1) NI "
	+ "ON N.NEWS_ID = NI.NEWS_ID";
private static String resultForFilterByAuthorForPage = "SELECT QRN.NEWS_ID, QRN.TITLE, QRN.SHORT_TEXT, QRN.FULL_TEXT, QRN.CREATION_DATE, QRN.MODIFICATION_DATE "
			+ "FROM ("
				+ "SELECT QR.*, ROWNUM RN "
				+ "FROM ("
					+ "SELECT N.* "
					+ "FROM NEWS N "
					+ "JOIN ("
						+ "SELECT NA.NEWS_ID "
						+ "FROM NEWS_AUTHOR NA "
						+ "WHERE NA.AUTHOR_ID = 1) NI "
					+ "ON N.NEWS_ID = NI.NEWS_ID"
				+ ") QR"
			+ ") QRN "
			+ "WHERE QRN.RN BETWEEN 1 AND 7";
private static String resultForFilterByAuthorAndTagsForPage = "SELECT QRN.NEWS_ID, QRN.TITLE, QRN.SHORT_TEXT, QRN.FULL_TEXT, QRN.CREATION_DATE, QRN.MODIFICATION_DATE "
					+ "FROM ("
						+ "SELECT QR.*, ROWNUM RN "
						+ "FROM ("
							+ "SELECT N.* "
							+ "FROM NEWS N "
							+ "JOIN ("
								+ "SELECT NA.NEWS_ID "
								+ "FROM NEWS_AUTHOR NA "
								+ "JOIN NEWS_TAG NT ON NT.NEWS_ID = NA.NEWS_ID "
								+ "WHERE NA.AUTHOR_ID = 1 AND NA.NEWS_ID IN ("
									+ "SELECT DISTINCT NT.NEWS_ID "
									+ "FROM NEWS_TAG NT "
									+ "WHERE NT.TAG_ID IN (1, 2, 3)"
								+ ") GROUP BY NA.NEWS_ID) NI "
							+ "ON N.NEWS_ID = NI.NEWS_ID"
						+ ") QR"
					+ ") QRN "
					+ "WHERE QRN.RN BETWEEN 1 AND 7";
	
	private static Long authorId = new Long(1);
	private static Long tag1Id = new Long(1);
	private static Long tag2Id = new Long(2);
	private static Long tag3Id = new Long(3);
	private static int FROM = 1;
	private static int TO = 7;
	@Test
	public void buildTest(){
		SearchParameter expectedSearchParameter = createSearchParameter();
		String expectedResult = FilterQueryBuilder.buildFilterQuery(expectedSearchParameter);
		Assert.assertEquals(expectedResult, resultForFilterByAuthorAndTags);
		expectedSearchParameter.setTagList(new ArrayList<Tag>());
		expectedResult = FilterQueryBuilder.buildFilterQuery(expectedSearchParameter);
		Assert.assertEquals(expectedResult, resultForFilterByAuthor);
	}
	@Test
	public void buildForPageTest(){
		SearchParameter expectedSearchParameter = createSearchParameter();
		String expectedResult = FilterQueryBuilder.buildFilterQuery(expectedSearchParameter, FROM, TO);
		Assert.assertEquals(expectedResult, resultForFilterByAuthorAndTagsForPage);
		expectedSearchParameter.setTagList(new ArrayList<Tag>());
		expectedResult = FilterQueryBuilder.buildFilterQuery(expectedSearchParameter, FROM,TO);
		Assert.assertEquals(expectedResult, resultForFilterByAuthorForPage);
	}
	private SearchParameter createSearchParameter(){
		SearchParameter expectedSearchParameter = new SearchParameter();
		Author expectedAuthor = new Author();
		expectedAuthor.setIdAuthor(authorId);
		Tag expectedTag1 = new Tag();
		Tag expectedTag2 = new Tag();
		Tag expectedTag3 = new Tag();
		expectedTag1.setIdTag(tag1Id);
		expectedTag2.setIdTag(tag2Id);
		expectedTag3.setIdTag(tag3Id);
		List<Tag> expectedListTags = new ArrayList<>();
		expectedListTags.add(expectedTag1);
		expectedListTags.add(expectedTag2);
		expectedListTags.add(expectedTag3);
		expectedSearchParameter.setAuthor(expectedAuthor);
		expectedSearchParameter.setTagList(expectedListTags);
		return expectedSearchParameter;
	}

}
