package nextstep.qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MetadataTest {

    @Test
    @DisplayName("기본 생성자로 Metadata를 생성하면 현재 시간이 생성 시간으로 설정된다")
    void testCreate_defaultConstructor() {
        // when
        Metadata metadata = new Metadata();
        
        // then
        assertThat(metadata.getCreatedDate()).isNotNull();
        assertThat(metadata.isDeleted()).isFalse();
    }
    
    @Test
    @DisplayName("지정된 생성 시간으로 Metadata를 생성할 수 있다")
    void testCreate_withCreatedDate() {
        // given
        LocalDateTime createdDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        
        // when
        Metadata metadata = new Metadata(createdDate);
        
        // then
        assertThat(metadata.getCreatedDate()).isEqualTo(createdDate);
        assertThat(metadata.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("delete 메서드는 삭제 상태와 시간을 설정한다")
    void testDelete() {
        // given
        Metadata metadata = new Metadata();
        
        // when
        metadata.delete();
        
        // then
        assertThat(metadata.isDeleted()).isTrue();
        assertThat(metadata.getDeletedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("동일한 시간과 상태를 가진 Metadata 객체는 동등하다")
    void testEquals() {
        // given
        LocalDateTime createdDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        Metadata metadata1 = new Metadata(createdDate);
        Metadata metadata2 = new Metadata(createdDate);
        
        // then
        assertThat(metadata1).isEqualTo(metadata2);
        assertThat(metadata1.hashCode()).isEqualTo(metadata2.hashCode());
    }
    
    @Test
    @DisplayName("삭제 후 getDeletedAt은 삭제 시간을 반환한다")
    void testGetDeletedAt() {
        // given
        Metadata metadata = new Metadata();
        
        // when
        metadata.delete();
        
        // then
        LocalDateTime deletedAt = metadata.getDeletedAt();
        assertThat(deletedAt).isNotNull();
    }
} 