@echo off
echo "Input is case sensitive"
set /p eName="Enter Entity Name: "
set /p tName="Enter Table Name: "
set /p ePath="Enter full path to model directory: "
set /p pName="Enter base package Name: "
set /p eType="Enter Entity Type(JPA/MONGO): "
IF NOT EXIST %ePath% (
 echo "Invalid directory"
 exit /B
)
IF NOT %eType%==JPA IF NOT %eType%==MONGO (
 echo "Invalid Entity Type"
 exit /B
)
cat RefModel.txt > %ePath%\%eName%.java
cat RefRepository.txt > %ePath%\..\repository\%eName%Repository.java
cat RefService.txt > %ePath%\..\service\%eName%Service.java
cat RefApi.txt > %ePath%\..\api\%eName%Api.java
powershell -Command "(gc %ePath%\%eName%.java) -replace 'pName', '%pName%' | Out-File %ePath%\%eName%.java"

powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'pName', '%pName%' | Out-File %ePath%\..\repository\%eName%Repository.java"
powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'eName', '%eName%' | Out-File %ePath%\..\repository\%eName%Repository.java"

powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'pName', '%pName%' | Out-File %ePath%\..\service\%eName%Service.java"
powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'eName', '%eName%' | Out-File %ePath%\..\service\%eName%Service.java"

powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'pName', '%pName%' | Out-File %ePath%\..\api\%eName%Api.java"
powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'eNameLower', '%eName%'.toLower() | Out-File %ePath%\..\api\%eName%Api.java"
powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'eName', '%eName%' | Out-File %ePath%\..\api\%eName%Api.java"

if %eType%==JPA (
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'enityImport', 'import javax.persistence.Entity;' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'tableImport', 'import javax.persistence.Table;' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'whereImport', 'import org.hibernate.annotations.Where;' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'enitityAnnotation', '@Entity' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'tableAnnotation', '@Table(name = \"%tName%\")' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'whereAnnotation', '@Where(clause = \"deleted = false\")' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'BaseModelToExtend', 'BaseJpaModel' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'BaseRepoToExtend', 'BaseJpaRepository' | Out-File %ePath%\..\repository\%eName%Repository.java"
	powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'baseIDType', 'Long' | Out-File %ePath%\..\repository\%eName%Repository.java"
	powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'BaseServiceToExtend', 'BaseJpaService' | Out-File %ePath%\..\service\%eName%Service.java"
	powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'baseIDType', 'Long' | Out-File %ePath%\..\service\%eName%Service.java"
	powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'BaseApiToExtend', 'BaseJpaApi' | Out-File %ePath%\..\api\%eName%Api.java"
	powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'baseIDType', 'Long' | Out-File %ePath%\..\api\%eName%Api.java"

) else (	
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'enityImport', '' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'tableImport', 'import org.springframework.data.mongodb.core.mapping.Document;' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'whereImport', '' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'enitityAnnotation', '' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'tableAnnotation', '@Document(collection = \"%tName%\")' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'whereAnnotation', '' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\%eName%.java) -replace 'BaseModelToExtend', 'BaseMongoModel' | Out-File %ePath%\%eName%.java"
	powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'BaseRepoToExtend', 'BaseMongoRepository' | Out-File %ePath%\..\repository\%eName%Repository.java"
	powershell -Command "(gc %ePath%\..\repository\%eName%Repository.java) -replace 'baseIDType', 'String' | Out-File %ePath%\..\repository\%eName%Repository.java"
	powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'BaseServiceToExtend', 'BaseMongoService' | Out-File %ePath%\..\service\%eName%Service.java"
	powershell -Command "(gc %ePath%\..\service\%eName%Service.java) -replace 'baseIDType', 'String' | Out-File %ePath%\..\service\%eName%Service.java"
	powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'BaseApiToExtend', 'BaseMongoApi' | Out-File %ePath%\..\api\%eName%Api.java"
	powershell -Command "(gc %ePath%\..\api\%eName%Api.java) -replace 'baseIDType', 'String' | Out-File %ePath%\..\api\%eName%Api.java"

)

powershell -Command "(gc %ePath%\%eName%.java) -replace 'eName', '%eName%' | Out-File %ePath%\%eName%.java"
	