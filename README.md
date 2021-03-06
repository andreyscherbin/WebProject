# Web-forum

## Project-overview

A web forum is a system where people can discuss a topic in the form of messages. Users create their own topics, with their subsequent discussion, by posting messages inside these topics. Users can comment on the declared topic, ask questions about it and get answers, as well as answer questions from other forum users and give them advice. Questions and answers are saved in the forum database, and in the future can be useful both to forum participants and to any Internet users who can enter the forum knowing the site address, or having received it from search engines when searching for information. Topics of the same subject are combined, for convenience, in the appropriate Sections. The hierarchy of the web forum: Sections -> Topics -> posts (posts). Compliance with the rules is monitored by moderators and administrators - participants endowed with the ability to edit, move and delete other people's messages in a specific section or topic, as well as control access to them for individual participants and visitors. The forum has the ability to search through its database of topics.

## Database model

![Database model](https://github.com/andreyscherbin/WebProject/blob/master/images/DatabaseModel.PNG)

## Use Case

## Admin

 Admin can do everything that the moderator, user, guest and also
   * Ban user
   * Unban user
   * Change user role
   * View all users
   * Delete section
   * Create section

## Moder

 Moder can do everything that the user, guest and also
   * Close topic
   * Pin topic
   * Delete topic

## User

 User can do everything that the guest and also
   * Create topic
   * Edit post
   * Delete post
   * Create post
   * Logout

## Guest

 Guest can do
   * Activation
   * Search topics
   * View topics
   * View posts
   * View sections
   * Change language  
   * Login
   * Registration
