package io.github.thecsdev.morepbadges;

import java.nio.file.InvalidPathException;
import java.time.LocalDateTime;

import io.github.thecsdev.tcdcommons.api.config.AutoConfig;

public final class MorePBadgesConfig extends AutoConfig
{
	// ==================================================
	/**
	 * When in "holiday-mode", some special events will happen.
	 */
	public static HolidayMode HOLIDAY_MODE = HolidayMode.NONE;
	// --------------------------------------------------
	// ==================================================
	public MorePBadgesConfig(String name) throws InvalidPathException { super(name); }
	// ==================================================
	public static enum HolidayMode { NONE, NEW_YEAR }
	// --------------------------------------------------
	public static final void updateHolidayMode()
	{
		synchronized(HOLIDAY_MODE)
		{
			//obtain the current time
			LocalDateTime now = LocalDateTime.now();
			final int dayOfMonth = now.getDayOfMonth();
			
			//check for new year's eve
			if(now.getMonthValue() == 1 && (dayOfMonth <= 3))
			{
				HOLIDAY_MODE = HolidayMode.NEW_YEAR;
				return;
			}
			
			//by default, no holidays
			HOLIDAY_MODE = HolidayMode.NONE;
		}
	}
	// ==================================================
}