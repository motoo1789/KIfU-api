/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.analyser;

import java.util.Set;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;

/**
 * A lexical analyzer
 *
 * @author Henri Darmet
 */

public class LexicalAnalyzer {
	/**
	 * Flag used by the lexical analyzer
	 *
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 *
	 */
	public enum LexicalFlag {
		/**
	 *
	 */
		CHAR,
		/**
	 *
	 */
		CHAR_DEFINED,
		/**
	 *
	 */
		DECIMAL,
		/**
	 *
	 */
		DOT_OR_DECIMAL,
		/**
	 *
	 */
		ESCAPED_CHAR,
		/**
	 *
	 */
		ESCAPED_STRING,
		/**
	 *
	 */
		EXPONENT,
		/**
	 *
	 */
		FLOAT,
		/**
	 *
	 */
		IDENTIFIER,
		/**
	 *
	 */
		INTEGER,
		/**
	 *
	 */
		NUMERIC,
		/**
	 *
	 */
		SIGN,
		/**
	 *
	 */
		SIGN_CONTINUED,
		/**
	 *
	 */
		SIGN_OR_NUMERIC,
		/**
	 *
	 */
		SIGNED_EXPONENT,
		/**
	 *
	 */
		START_DECIMAL,
		/**
	 *
	 */
		START_EXPONENT,
		/**
	 *
	 */
		STRING,
		/**
	 *
	 */
		UNDEFINED,
		/**
	 *
	 */
		VISIBILITY;
	}

	/**
	 * @author Henri Darmet
	 */
	public static class Token {

		String		content;
		LexicalFlag	type;

		/**
		 * Token
		 *
		 * @param type
		 * @param content
		 */
		public Token(final LexicalFlag type, final String content) {
			super();
			this.type = type;
			this.content = content;
		}

		/**
		 * Getter for the token content
		 *
		 * @return the content of the token
		 *
		 */
		public String getContent() {
			return this.content;
		}

		/**
		 * Getter for the token type
		 *
		 * @return the type of the token
		 *
		 * @see LexicalFlag
		 */
		public LexicalFlag getType() {
			return this.type;
		}
	}

	int				ptr;
	LexicalFlag		status				= LexicalFlag.UNDEFINED;
	String			text;

	StringBuilder	tokenStringBuilder	= new StringBuilder();

	/**
	 * Constructor of LexicalAnalyzer
	 *
	 * @param text
	 */
	public LexicalAnalyzer(final String text) {
		super();
		this.text = text;
		this.ptr = 0;
	}

	/**
	 * Getter for the token
	 *
	 * @return the token
	 */
	public Token getToken() {
		Token token = null;
		while (token == null) {
			if (this.ptr >= this.text.length()) {
				if (this.tokenStringBuilder.length() > 0) {
					token = this.processEOF();
					if (token != null) {
						return token;
					}
					throw new GWTUMLAPIException("Unexpected EOF");
				}
				return null;
			}
			token = this.processNextChar();
		}
		return token;
	}

	Token consume(final LexicalFlag consumeStatus, final char c) {
		this.tokenStringBuilder.append(c);
		this.ptr++;
		final String content = this.tokenStringBuilder.toString();
		this.tokenStringBuilder = new StringBuilder();
		this.status = LexicalFlag.UNDEFINED;
		return new Token(consumeStatus, content);
	}

	Token ignore() {
		this.ptr++;
		return null;
	}

	Token inject(final LexicalFlag injectStatus) {
		final String content = this.tokenStringBuilder.toString();
		this.tokenStringBuilder = new StringBuilder();
		this.status = LexicalFlag.UNDEFINED;
		return new Token(injectStatus, content);
	}

	Token process(final LexicalFlag processStatus, final char c) {
		this.tokenStringBuilder.append(c);
		this.ptr++;
		this.status = processStatus;
		return null;
	}

	Token processEOF() {
		switch (this.status) {
			case VISIBILITY:
				return this.inject(LexicalFlag.VISIBILITY);
			case IDENTIFIER:
				return this.inject(LexicalFlag.IDENTIFIER);
			case SIGN_OR_NUMERIC:
				return this.inject(LexicalFlag.SIGN);
			case DOT_OR_DECIMAL:
				return this.inject(LexicalFlag.SIGN);
			case NUMERIC:
				return this.inject(LexicalFlag.INTEGER);
			case DECIMAL:
				return this.inject(LexicalFlag.FLOAT);
			case EXPONENT:
				return this.inject(LexicalFlag.FLOAT);
			case SIGN_CONTINUED:
				return this.inject(LexicalFlag.SIGN);
		}
		return null;
	}

	private	final Set<Character> notIdentifierIntial = new java.util.HashSet<Character>(java.util.Arrays.asList(
			'#',
			'(',
			')',
			',',
			'{',
			'}',
			':',
			'[',
			']',
			'=',
			'<',
			'>',
			'+',
			'-',
			'.',
			'\'',
			'"',
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9'
	));

	private	final Set<Character> notIdentifier = new java.util.HashSet<Character>(java.util.Arrays.asList(
			'#',
			'(',
			')',
			',',
			'{',
			'}',
			':',
			'[',
			']',
			'=',
			'<',
			'>',
			'+',
			'-',
			'.',
			'\'',
			'"'
	));

	//TODO
	@SuppressWarnings("fallthrough")
	Token processNextChar() {
		final char c = this.text.charAt(this.ptr);
		switch (this.status) {
			case UNDEFINED:
				if (c == ' ') {
					return this.ignore();
				} else if ((c == '#') || (c == '+') || (c == '-') || (c == '~')) {
					return this.process(LexicalFlag.VISIBILITY, c);
				} else if ( ! notIdentifierIntial.contains(c) ){
					return this.process(LexicalFlag.IDENTIFIER, c);
				} else if ((c == '#') || (c == '(') || (c == ')') || (c == ',') || (c == '{') || (c == '}') || (c == ':') || (c == '[') || (c == ']')
						|| (c == '=')) {
					return this.consume(LexicalFlag.SIGN, c);
				} else if ((c == '<') || (c == '>')) {
					return this.process(LexicalFlag.SIGN_CONTINUED, c);
				} else if ((c == '+') || (c == '-')) {
					return this.process(LexicalFlag.SIGN_OR_NUMERIC, c);
				} else if (c == '.') {
					return this.process(LexicalFlag.DOT_OR_DECIMAL, c);
				} else if (c == '\'') {
					return this.process(LexicalFlag.CHAR, c);
				} else if (c == '"') {
					return this.process(LexicalFlag.STRING, c);
				} else if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.NUMERIC, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case VISIBILITY:
				return this.inject(LexicalFlag.VISIBILITY);
			case IDENTIFIER:
				if (! notIdentifier.contains(c)){
					return this.process(LexicalFlag.IDENTIFIER, c);
				}

				return this.inject(LexicalFlag.IDENTIFIER);
			case SIGN_OR_NUMERIC:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.NUMERIC, c);
				} else if (c == '.') {
					return this.process(LexicalFlag.DECIMAL, c);
				}

				return this.inject(LexicalFlag.SIGN);
			case SIGN_CONTINUED:
				if (c == '=') {
					return this.consume(LexicalFlag.SIGN, c);
				}

				return this.inject(LexicalFlag.SIGN);
			case DOT_OR_DECIMAL:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.START_DECIMAL, c);
				}

				return this.inject(LexicalFlag.SIGN);
			case STRING:
				if (c == '\\') {
					return this.process(LexicalFlag.ESCAPED_STRING, c);
				} else if (c == '"') {
					return this.consume(LexicalFlag.STRING, c);
				}

			case ESCAPED_STRING:
				return this.process(LexicalFlag.STRING, c);
			case CHAR:
				if (c == '\\') {
					return this.process(LexicalFlag.ESCAPED_CHAR, c);
				} else if (c != '\'') {
					return this.process(LexicalFlag.CHAR_DEFINED, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case ESCAPED_CHAR:
				return this.process(LexicalFlag.CHAR_DEFINED, c);
			case CHAR_DEFINED:
				if (c == '\'') {
					return this.consume(LexicalFlag.CHAR, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case NUMERIC:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.NUMERIC, c);
				} else if (c == '.') {
					return this.process(LexicalFlag.DECIMAL, c);
				} else if ((c == 'e') || (c == 'E')) {
					return this.process(LexicalFlag.SIGNED_EXPONENT, c);
				} else {
					return this.consume(LexicalFlag.INTEGER, c);
				}
			case START_DECIMAL:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.DECIMAL, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case DECIMAL:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.DECIMAL, c);
				} else if ((c == 'e') || (c == 'E')) {
					return this.process(LexicalFlag.SIGNED_EXPONENT, c);
				}
				return this.inject(LexicalFlag.FLOAT);

			case SIGNED_EXPONENT:
				if ((c == '+') || (c == '-')) {
					return this.process(LexicalFlag.START_EXPONENT, c);
				} else if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.EXPONENT, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case START_EXPONENT:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.EXPONENT, c);
				}
				throw new GWTUMLAPIException("Invalid character : " + c);
			case EXPONENT:
				if ((c >= '0') && (c <= '9')) {
					return this.process(LexicalFlag.EXPONENT, c);
				}

				return this.inject(LexicalFlag.FLOAT);
		}
		throw new GWTUMLAPIException("Invalid status : " + this.status);
	}
}
