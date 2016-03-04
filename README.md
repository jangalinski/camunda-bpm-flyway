# camunda-bpm-flyway

_Setting up (camunda) database with flyway migration._

It is very likely that you want to do more than just letting camunda create schemas when the server starts (on a fresh db).
There are users to create, maybe a license file to install and some properties to configure.
And if you are using a shared schema for camunda and your business domain (often seen pattern because it allows custom queries on 
tasks and data), you will have to even create/alter custom tables on the fly.

What you need is somekind of db-migration. I am working with flyway but had the problem that camundas mybatis lifecycle interfered 
with my flyway scripts (basically when defining what a baseline is and who touches the db first).

So I came up with this  small extension reads the db create scripts camunda provides and uses them as flyway migration scripts, 
meaning I can easily provide my own custom migrations scripts as well and flyway will use the correct version/combination for all of 
them.

Usage is really simple, there is just a new JdbcMigration provided and flyway is configured to use it.

see [CamundaFlywayTest](./src/test/java/org/camunda/bpm/extension/flyway/CamundaFlywayTest.java) for example.
