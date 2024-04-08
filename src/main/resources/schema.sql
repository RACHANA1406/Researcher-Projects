create table if not exists project(
    id int primary key AUTO_INCREMENT,
    name varchar(250),
    budget double
);
CREATE table if not exists researcher(
   id int primary key AUTO_INCREMENT,
   name varchar(250),
   specialization varchar(250)
);
create table if not exists researcher_project
(
    researcherId int,
    projectId int,
    primary key(researcherId,projectId),
    foreign key(researcherId) references researcher(id),
    foreign key(projectId) references project(id)
);