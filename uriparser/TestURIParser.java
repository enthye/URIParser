package uriparser; // DO NOT CHANGE THIS OR YOU WILL GET ZERO

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;

/**
 * SOFTENG 254 2018 Assignment 1 submission
 *
 * Author: Kevin Xu kxu015
 **/

public class TestURIParser {// DO NOT CHANGE THE CLASS NAME OR YOU WILL GET ZERO

	/**
	 * Good and simple tests that tries to cover all possible faults of a URIParser
	 * when parsing typical and atypical but valid URIs.
	 * 
	 * References: RFC3986 https://tools.ietf.org/html/rfc3986#section-7.4
	 * https://en.wikipedia.org/wiki/Uniform_Resource_Identifier
	 * https://stackoverflow.com/questions/4913343/what-is-the-difference-between-uri-url-and-urn
	 * URIParser, URI and ParseException JavaDocs
	 */
	private URIParser uriparser;

	/**
	 * Initialise a URIparser before each test, so the test can use the input
	 * URIParser.  Reduce code duplication!
	 */
	@Before
	public void initialise() {
		uriparser = new URIParser();
	}

	/**
	 * Tests a typical URL type of URI, like an Internet link. If the URIParser
	 * cannot pass this test, then it is a very poorly written URIParser.
	 */
	@Test
	public void parseTypicalUri() {
		URI uri = uriparser.parse("a://b.c.d/e/f.g?h=i#j");

		assertEquals("a", uri.getScheme());
		assertEquals("b.c.d", uri.getAuthority());
		assertEquals("/e/f.g", uri.getPath());
		assertEquals("h=i", uri.getQuery());
		assertEquals("j", uri.getFragment());
	}

	/**
	 * An empty URI string should parse, but the URI should be null, because there
	 * is "nothing" for any of the segments of the URI to assign to.
	 */
	@Test
	public void parseEmptyUri() {
		URI uri = uriparser.parse("");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Parse a space " ", because it should be an invalid URI, and the parser should
	 * treat it as such by throwing an exception.
	 */
	@Test
	public void parseEmptySpace() {
		try {
			URI uri = uriparser.parse(" ");
			fail("Failed parseEmptySpace"); // should not be able to parse a space string
		} catch (ParseException e) {
			// ParseException thrown = pass test!
		}
	}

	/**
	 * A typical parsing null test, null is an invalid URI, and should always throw
	 * an exception.
	 */
	@Test
	public void parseNullUri() {
		try {
			URI uri = uriparser.parse(null);
			fail("Failed parseNullUri"); // should not be able to parse null
		} catch (ParseException e) {
			// ParseException thrown = pass test!
		}
	}

	/**
	 * Testing similar example in Javadoc for URIParser where it had an empty
	 * authority, an empty authority is a valid authority, and should not parse '/'
	 * as authority.
	 */
	@Test
	public void parseEmptyAuthNoQueryFragUri() {

		URI uri = uriparser.parse("a:///b/c");

		assertEquals("a", uri.getScheme());
		assertEquals("", uri.getAuthority()); // equivalent to 'a://""/b/c'
		assertEquals("/b/c", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Test where it is mostly an "empty URI", except for the scheme. It needs a
	 * scheme otherwise any URI with an "empty scheme" will just be a path.
	 */
	@Test
	public void parseEmptyExceptSchemeUri() {

		URI uri = uriparser.parse("a://?#");

		assertEquals("a", uri.getScheme());
		assertEquals("", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("", uri.getQuery());
		assertEquals("", uri.getFragment());
	}

	/**
	 * Tests if an important type of URI, mailto: email addresses can be URIParsed
	 */
	@Test
	public void parseMailToUri() {
		URI uri = uriparser.parse("mailto:e.tempero@auckland.ac.nz");

		assertEquals("mailto", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("e.tempero@auckland.ac.nz", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * An URN (Universal Resource Name) describes a name, but not a location (URL).
	 * This tests if the parser can parse a typical URN, URNs have no authority.
	 */
	@Test
	public void parseUrnUri() {
		URI uri = uriparser.parse("urn:rfc:3986");

		assertEquals("urn", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("rfc:3986", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Naturally, we want to test an empty URN as well.
	 */
	@Test
	public void parseColonUri() {

		URI uri = uriparser.parse("urn::::"); // would only look at semicolons after first semicolon

		assertEquals("urn", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(":::", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Tests scheme with no authority, so it has no double slashes, otherwise, it
	 * becomes a URL.
	 */
	@Test
	public void parseSchemePathUri() {
		URI uri = uriparser.parse("a:/b");

		assertEquals("a", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("/b", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Test scheme with no query, to see if it parses query normally.
	 */
	@Test
	public void parseSchemeQueryUri() {
		URI uri = uriparser.parse("a:?b");

		assertEquals("a", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("b", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Test scheme with no fragment, to see if it parses fragment normally.
	 */
	@Test
	public void parseSchemeFragmentUri() {
		URI uri = uriparser.parse("a:#b");
		assertEquals("a", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("b", uri.getFragment());
	}

	/**
	 * This tests will see if a parser can correctly parse a link with a slash at
	 * the end of each stage of the URI. This is because / seem to be an important
	 * bridge between distinguishing authority and path and ending a URI.
	 */
	@Test
	public void parseEndSlashUri() {
		URI uri = uriparser.parse("/");
		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseEndDoubleSlashUri() {
		URI uri = uriparser.parse("//");

		assertEquals(null, uri.getScheme());
		assertEquals("", uri.getAuthority()); // now 2 slashes represent authority
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * IP addresses are also common URIs, so this test will cover most IP URIs
	 */
	@Test
	public void parseIPUri() {

		URI uri = uriparser.parse("http://1.0.0.0:80/");

		assertEquals("http", uri.getScheme());
		assertEquals("1.0.0.0:80", uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * Phone numbers are also common URIs, so this test will cover most phone URIs
	 */
	@Test
	public void parsePhoneNumberUri() {

		URI uri = uriparser.parse("tel:+64-9-923-3765");

		assertEquals("tel", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("+64-9-923-3765", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * This test focuses on only the scheme only part of the URI, it should pick up
	 * an empty authority.
	 */
	@Test
	public void parseSchemeOnlyUri() {
		URI uri = uriparser.parse("scheme:");

		assertEquals("scheme", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * This tests focuses on all possible "empty" scheme, to see if it ignores
	 * parsing scheme as well as authority.
	 */
	@Test
	public void parseEmptySchemeFullUri() {
		URI uri = uriparser.parse("://a?b#c");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("://a", uri.getPath());
		assertEquals("b", uri.getQuery());
		assertEquals("c", uri.getFragment());
	}

	/**
	 * This test focuses the parser on only the authority part of the URI.
	 */
	@Test
	public void parseAuthorityOnlyUri() {
		URI uri = uriparser.parse("//auth");

		assertEquals(null, uri.getScheme());
		assertEquals("auth", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * This test focuses the parser on only the path part of the URI.
	 */
	@Test
	public void parsePathOnlyUri() {
		URI uri = uriparser.parse("path");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("path", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * This test focuses the parser on only the query part of the URI.
	 */
	@Test
	public void parseQueryOnlyUri() {
		URI uri = uriparser.parse("??query");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("?query", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * This test focuses the parser on only fragment part of the URI.
	 */
	@Test
	public void parseFragmentOnlyUri() {
		URI uri = uriparser.parse("##frag");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("#frag", uri.getFragment());
	}

	/**
	 * Test if legal strings actually work as stated "$", "-", "_", ".", "+", "!",
	 * "*", "'" (single quote), "(", ")", ",", ";", "/", "?", ":", "@", "&", "=",
	 * "%", "#".
	 */
	@Test
	public void parseLegalStringsUri() {
		URI uri = uriparser.parse("$-_.://+!*'/(),;?/?:@#&=%#");

		assertEquals("$-_.", uri.getScheme());
		assertEquals("+!*'", uri.getAuthority());
		assertEquals("/(),;", uri.getPath());
		assertEquals("/?:@", uri.getQuery());
		assertEquals("&=%#", uri.getFragment());
	}

	/**
	 * These tests covers all the bad characters (:/?#) in scheme as stated in the Javadoc
	 */
	@Test
	public void parseBadSchemeCharacter1Uri() {
		URI uri = uriparser.parse(":://");
		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(":://", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadSchemeCharacter2Uri() {
		URI uri = uriparser.parse("/://");
		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("/://", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadSchemeCharacter3Uri() {
		URI uri = uriparser.parse("?://");
		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("://", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadSchemeCharacter4Uri() {
		URI uri = uriparser.parse("#://");
		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("://", uri.getFragment());
	}

	/**
	 * A scheme connects to authority via <scheme>":" "//"<authority>. This test
	 * breaks that connection between the scheme and authority, and the outcome
	 * should be that there is no authority.
	 */
	@Test
	public void parseBrokenSchemeToAuthUri() {
		URI uri = uriparser.parse("a:/");

		assertEquals("a", uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	/**
	 * These tests cover all bad characters (/?#) in the authority as stated by Javadoc.
	 */
	@Test
	public void parseBadAuthCharacter1Uri() {
		URI uri = uriparser.parse("http:///");

		assertEquals("http", uri.getScheme());
		assertEquals("", uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadAuthCharacter2Uri() {
		URI uri = uriparser.parse("http://?");
		assertEquals("http", uri.getScheme());
		assertEquals("", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadAuthCharacter3Uri() {
		URI uri = uriparser.parse("http://#");
		assertEquals("http", uri.getScheme());
		assertEquals("", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("", uri.getFragment());
	}

	/**
	 * Tests all bad characters (?#) in the path as stated by Javadoc.
	 */
	@Test
	public void parseBadPathCharacter1Uri() {
		URI uri = uriparser.parse("http://a/?");
		assertEquals("http", uri.getScheme());
		assertEquals("a", uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals("", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parseBadPathCharacter2Uri() {
		URI uri = uriparser.parse("http://a/#");
		assertEquals("http", uri.getScheme());
		assertEquals("a", uri.getAuthority());
		assertEquals("/", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("", uri.getFragment());
	}

	/**
	 * Tests bad character (#) in the query as stated by Javadoc.
	 */
	@Test
	public void parseBadQueryCharacterUri() {
		URI uri = uriparser.parse("http://a/b?#");
		assertEquals("http", uri.getScheme());
		assertEquals("a", uri.getAuthority());
		assertEquals("/b", uri.getPath());
		assertEquals("", uri.getQuery());
		assertEquals("", uri.getFragment());
	}

	/**
	 * Tests focuses on path only with query and fragment
	 */
	@Test
	public void parsePathWithQuery() {
		URI uri = uriparser.parse("a?b");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("a", uri.getPath());
		assertEquals("b", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}

	@Test
	public void parsePathWithFragment() {
		URI uri = uriparser.parse("a#b");

		assertEquals(null, uri.getScheme());
		assertEquals(null, uri.getAuthority());
		assertEquals("a", uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("b", uri.getFragment());
	}

	/**
	 * These tests focuses on an authority with only with query or a fragment
	 */
	@Test
	public void parseAuthorityWithQuery() {
		URI uri = uriparser.parse("//a?b");

		assertEquals(null, uri.getScheme());
		assertEquals("a", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals("b", uri.getQuery());
		assertEquals(null, uri.getFragment());
	}
	@Test
	public void parseAuthorityWithFragment() {
		URI uri = uriparser.parse("//a#b");
		assertEquals(null, uri.getScheme());
		assertEquals("a", uri.getAuthority());
		assertEquals(null, uri.getPath());
		assertEquals(null, uri.getQuery());
		assertEquals("b", uri.getFragment());
	}
}
