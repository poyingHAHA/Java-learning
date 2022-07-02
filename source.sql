create table Video(
  videoId int auto_increment primary key,
  videoName varchar(50) null,
  price int null,
  unique(videoId)
)