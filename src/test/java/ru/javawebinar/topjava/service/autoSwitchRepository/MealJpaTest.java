package ru.javawebinar.topjava.service.autoSwitchRepository;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTestBase;

@ActiveProfiles(profiles = Profiles.JPA)
public class MealJpaTest extends MealServiceTestBase {
}