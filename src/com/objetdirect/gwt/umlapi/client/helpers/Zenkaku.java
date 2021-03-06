package com.objetdirect.gwt.umlapi.client.helpers;



public class Zenkaku
{
	/**
	 * Whether str is all zentaku characters or not.
	 * @param str Target characters
	 * @return True if str is all zenkaku, false if not
	 */
	public boolean isZenkaku ( String str )
	{
		// Regular expression.
		return str.matches("[^ -~｡-ﾟ]*");
	}

	/**
	 * Whether str includes zentaku or not.
	 * @param str Target characters
	 * @return True if str includes zentaku, false if not.
	 */
	public boolean includeZenkaku ( String str )
	{
		boolean ret_val = false;
		for( char ch : str.toCharArray())
		{
			char chs[] = new char[1];
			chs[0] = ch;

			String st = new String(chs);
			if ( isZenkaku ( st ) )
			{
				ret_val = true;
			}
		}
		return ret_val;
	}

	/**
	 * Check str includes zenkaku or hankaku.
	 * @param str
	 * @return BitSet(if the character is zenkaku, set bit)
	 */
	public boolean[] charsCheck ( String str )
	{
		boolean[] ret_val = new boolean[str.length()];

		int count = 0;
		for( char ch : str.toCharArray())
		{
			char chs[] = new char[1];
			chs[0] = ch;

			String st = new String(chs);
			if ( isZenkaku ( st ) )
			{
				ret_val[count]=true;
			}
			else{
				ret_val[count]=false;
			}
			count++;
		}
		return ret_val;
	}

	/**
	 * Return what kind of characters set?
	 * @param str Target characters
	 * @return 0: All hankaku, 1: All zenkaku,
	 */
	public int validateChars ( String str )
	{
		int ret_val = 0;
		if ( isZenkaku ( str ) )
		{
			// All zenkaku.
			ret_val = 1;
		}
		else if ( includeZenkaku( str ) )
		{
			// At least one character is zenkaku.
			ret_val = 2;
		}
		return ret_val;
	}
}
