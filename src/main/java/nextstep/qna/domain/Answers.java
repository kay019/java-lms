package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import nextstep.users.domain.NsUser;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        Objects.requireNonNull(answers, "답변 목록은 null이 될 수 없습니다.");
        this.answers = new ArrayList<>(answers);
    }

    public void add(Answer answer) {
        Objects.requireNonNull(answer, "답변은 null이 될 수 없습니다.");
        answers.add(answer);
    }

    public boolean isEmpty() {
        return answers.isEmpty();
    }

    public boolean contains(Answer answer) {
        return answers.contains(answer);
    }

    public int size() {
        return answers.size();
    }

    public boolean isAllDeleted() {
        if (isEmpty()) {
            return true;
        }
        return answers.stream().allMatch(Answer::isDeleted);
    }

    public boolean hasOtherOwner(NsUser user) {
        Objects.requireNonNull(user, "사용자는 null이 될 수 없습니다.");
        if (isEmpty()) {
            return false;
        }
        return answers.stream().anyMatch(answer -> !answer.isOwner(user));
    }

    public void deleteAll() {
        answers.forEach(Answer::delete);
    }

    public List<DeleteHistory> createDeleteHistories() {
        return answers.stream().filter(Answer::isDeleted).map(Answer::createDeleteHistory).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answers);
    }

    @Override
    public String toString() {
        return "Answers{" + "count=" + answers.size() + ", answers=" + answers + '}';
    }
} 