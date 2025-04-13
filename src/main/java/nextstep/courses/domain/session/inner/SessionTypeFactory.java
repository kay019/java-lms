package nextstep.courses.domain.session.inner;

import java.lang.reflect.Constructor;
import java.util.List;

public class SessionTypeFactory {

    private static final List<Class<? extends SessionType>> REGISTERED_TYPES = List.of(
            FreeSessionType.class,
            PaidSessionType.class
    );

    public static SessionType from(String type, int maxEnrollment, long fee) {
        for (Class<? extends SessionType> clazz : REGISTERED_TYPES) {
            try {
                String typeField = (String) clazz.getDeclaredField("TYPE").get(null);
                if (typeField.equalsIgnoreCase(type)) {
                    Constructor<? extends SessionType> constructor = clazz.getConstructor(int.class, long.class);
                    return constructor.newInstance(maxEnrollment, fee);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to create SessionType for type: " + type, e);
            }
        }
        throw new IllegalArgumentException("Unknown session type: " + type);
    }
}
