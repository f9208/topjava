package ru.javawebinar.topjava.service.autoSwitchRepository;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTestBase;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaTest extends UserServiceTestBase {
}