package com.brodygaudel.bank.common.util.implementation;

import com.brodygaudel.bank.common.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of the {@link IdGenerator} interface that generates unique IDs
 * based on the current date, time, and a counter. The counter is reset to 0 every hour.
 */

@Service
public class IdGeneratorImpl implements IdGenerator {

    /** Atomic counter for generating unique IDs. */
    private final AtomicLong counter = new AtomicLong(0);

    /** Timestamp of the last counter reset. */
    private long lastResetHour = 0;

    /** Lock for ensuring thread-safety during ID generation. */
    private static final Lock lock = new ReentrantLock();

    /**
     * Generates a unique ID based on the current date, time, and a counter.
     *
     * @return A unique ID.
     */
    @Override
    public String autoGenerate() {
        // Get the current date and time
        Date now = new Date();

        // Check if the current hour is different from the last reset hour
        if (isDifferentHour(now.getTime(), lastResetHour)) {
            // If yes, reset the counter to 0
            counter.set(0);
            // Update the last reset hour
            lastResetHour = now.getTime();
        }

        // Format the date and time according to the desired pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String formattedDateTime = dateFormat.format(now);

        // Use a lock to ensure exclusive access to subsequent operations.
        lock.lock();
        try {
            // Increment the counter safely
            long currentCounter = counter.incrementAndGet();
            // Generate the identifier by concatenating the date, formatted time, and counter
            return formattedDateTime + String.format("%06d", currentCounter);
        } finally {
            // Release the lock in all cases, even in exceptional cases
            lock.unlock();
        }
    }


    /**
     * Checks if two timestamps represent different hours.
     *
     * @param time1 The first timestamp.
     * @param time2 The second timestamp.
     * @return {@code true} if the hours are different, {@code false} otherwise.
     */
    private boolean isDifferentHour(long time1, long time2) {
        // Check if the hours are different
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);

        return cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY);
    }
}
