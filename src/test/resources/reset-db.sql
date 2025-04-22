delete
from session;
delete
from session_student;
delete
from cover_image;

alter table session
    alter column id restart with 1;
alter table session_student
    alter column id restart with 1;
alter table cover_image
    alter column id restart with 1;
