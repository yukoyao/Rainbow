
以下内容将参考 github jgitver-maven-plugin 文档内容: 

github 地址: [github地址](https://github.com/jgitver/jgitver-maven-plugin/blob/master/README.md)

#### 插件功能说明

此插件允许使用来自 git history 记录的信息来定义项目的 pom 版本。

#### 插件用法

1. 在项目的根目录创建 `.mvn` 文件夹
2. 创建文件 `.mvn/extensions.xml` 
3. 将下面内容放入 `extensions.xml` 中

```
<extensions xmlns="http://maven.apache.org/EXTENSIONS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/EXTENSIONS/1.0.0 http://maven.apache.org/xsd/core-extensions-1.0.0.xsd">
  <extension>
    <groupId>fr.brouillard.oss</groupId>
    <artifactId>jgitver-maven-plugin</artifactId>
    <version>1.8.0</version>
  </extension>
</extensions>
```

#### 配置

为了控制 jgitver-maven-plugin 的行为, 可以在 .mvn/jgitver.config.xml 编写配置。

```
<configuration xmlns="http://jgitver.github.io/maven/configuration/1.1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://jgitver.github.io/maven/configuration/1.1.0 https://jgitver.github.io/maven/configuration/jgitver-configuration-v1_1_0.xsd">
	
	<strategy>MAVEN|CONFIGURABLE|PATTERN</strategy>
	<policy>MAX|LATEST|NEAREST</policy>     <!-- policy 用于选择用于版本计算的基本标记/提交 -->
	<autoIncrementPatch>true/false</autoIncrementPatch>
	<useCommitDistance>true/false</useCommitDistance>
	<useDirty>true/false</useDirty>
	<useGitCommitId>true/false</useGitCommitId>
	<useSnapshot></useSnapshot>      <!-- 在 CONFIGURABLE 策略中使用 -SNAPSHOT -->
	<gitCommitIdLength>integer</gitCommitIdLength> <!-- [8, 40] 之间-->
	<maxSearchDepth>integer</maxSearchDepth>    <!-- 大于等于1，否则省略， 默认为无限 -->	
	<nonQualifierBranches>master</nonQualifierBranches> <!-- 逗号分隔，例如: master, integeration -->
	<regexVersionTag>r([0-9]+)</regexVersionTag> <!-- 正则表达式，表示仅匹配 r0 ... r34 形式的标签 -->
	
	<exclusions>  <!-- 目录路径可选列表 -->
	    <exclusion>相对目录路径</exclusion>
	</exclusions>
	<useDefaultBranchingPolicy>true/false</useDefaultBranchingPolicy> <!-- 使用 jgitver#BranchingPolicy#Default_FALLBACK 作为后备分支策略 -->
	<branchPolicies>
	    <branchPolicy>
	        <pattern>pattern</pattern>    <!-- 正则匹配规则 -->
	        
	        <!-- 要应用的转换列表，如果为空，则默认为 REPLACE_UNEXPECTED_CHARS_UNDERSCORE, LOWERCASE_EN -->
	        <transformations>
	            <transformation>NAME</transformation> <!-- 转换名称 jgitver#fr.brouillard.oss.jgitver.BranchingPolicy#BranchNameTransformation 之一 -->
	        </transformations>
	    </branchPolicy>
	</branchPolicies>
	
</configuration>
```
