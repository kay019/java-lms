package nextstep.courses.service;

import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import nextstep.courses.entity.SessionEntity;
import nextstep.stub.TestImageHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void createSession_성공() throws IOException {
        Long courseId = 1L;
        SessionConstraint constraint = new SessionConstraint(100, 80);
        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", new TestImageHandler(300, 200, 1024L * 866L), SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );

        sessionService.createSession(courseId, constraint, descriptor);

        verify(sessionRepository, times(1)).save(any(SessionEntity.class));
    }

//    @Test
//    void deleteSession_성공() throws IOException {
//        long sessionId = 1L;
//        SessionEntity sessionEntity = mock(SessionEntity.class);
//
//        when(sessionRepository.findById(sessionId)).thenReturn(sessionEntity);
//
//        try (MockedStatic<Session> mockedStaticSession = mockStatic(Session.class)) {
//            Session sessionMock = mock(Session.class);
//
//            mockedStaticSession
//                .when(() -> Session.from(sessionEntity))
//                .thenReturn(sessionMock);
//
//            sessionService.deleteSession(sessionId);
//
//            verify(sessionMock, times(1)).delete();
//        }
//    }
}
