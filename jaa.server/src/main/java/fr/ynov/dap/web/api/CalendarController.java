package fr.ynov.dap.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.services.google.CalendarService;

/**
 * @author adrij
 *
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    /**
     * get the calendar service thanks Spring.
     */
    @Autowired
    private CalendarService calendarService;


    /**
     * Get the next Event from now.
     * @param userKey user key needed for authentication
     * @return near event from now
     * @throws Exception exception
     */
    @RequestMapping(value = "/event/next", method = RequestMethod.GET)
    public Event getNextEvent(@RequestParam("userKey") final String userKey) throws Exception {
        return calendarService.getNextEventForAllAccount(userKey);
    }
}
