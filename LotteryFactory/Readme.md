### Setup
**MongoDB Location**
```
/usr/local/Cellar/mongodb/3.2.10
```

**Starting MongoDB locally**
```
sudo mongod
```

### Testing
```
$ sbt test
$ sbt "test-only <Fully_Qualified_Class_Name>"
```

### Integration Testing

```
sbt it:test-only
```