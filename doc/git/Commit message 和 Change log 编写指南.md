
### Commit message 和 Change log 编写指南

#### Commit message 的作用

1. 提供更多的历史信息，方便快速流浪

```
// 下面命令显示上次发布后的变动，每个 commit 占一行。只需要看行首，就能知道此次提交的目的。

$ git log <last tag> HEAD --pretty=format:%s
```

![1](http://yuko.top:9099/images/2022/02/18/bg2016010604.png)

2. 可以过滤某些 commit，便于快速查找信息

```
// 下面命令仅显示本次发布增加的功能

$ git log <last release> HEAD --grep feature
```

3. 可以直接从 commit 中生成 Change log

Change Log 是发布新版本时，用来说明与上一个版本差异的文档。

![2](http://yuko.top:9099/images/2022/02/18/bg2016010603.png)


#### Commit message 的格式

每次提交，Commit message 都包括三个部分：`Header`、`Body`、`Footer`

```
<type>(<scope>): <subject>
// 空一行
<body>
// 空一行
<footer>
```

其中，Header 是必须的，Body、Footer 是可以省略的。

##### Header

* (1) type

`type` 用于说明 commit 的级别，只允许使用下面 7 个标志。

```
1. feat: 新功能(feature)
2. fix: 修改 bug
3. docs: 文档(documentation)
4. style: 格式(不影响代码运行的变动)
5. refactor: 重构(即不是新增功能，也是修改 bug 的代码变动)
6. test: (增加测试)
7. chore: 构建过程或辅助工具的变动
```

如果 type 为  feat 和 fix，则该 commit 肯定出现在 Change log 之中。其他视情况而定。

* (2) scope

`scope` 用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。

* (3) subject

`subject` 是 commit 目的的简短描述，不超过 50 个字符。

```
1. 以动词开头，使用第一人称现在时
2. 第一个字母小写
3. 结尾不加句号
```

##### Body

Body 部分是对本次 commit 的详细描述，可以分为多行。

```
fix VariationalDropoutLayer when embedding_wise_variational_dropout is false

- fix RocketLaunching prediction dict
- fix feature config order
- refactor feature importance visualize
```

##### Footer

Footer 只用于两种情况。

* 不兼容变动.如果当前代码与上一个版本不兼容，则 Footer 部分以 `BREAKING CHANGE`开头，后面是对变动的描述、以及变动理由和迁移方法。

```
BREAKING CHANGE: isolate scope bindings definition has changed.

    To migrate the code follow the example below:

    Before:

    scope: {
      myAttr: 'attribute',
    }

    After:

    scope: {
      myAttr: '@',
    }

    The removed `inject` wasn't generaly useful for directives so there should be no code using it.
```


* 关闭 Issue。如果当前 commit 针对某个 issue，那么可以在 Footer 部分关闭这个 issue.

```
// 关闭一个
Closes #234
// 关闭多个
Closes #234，#245，#992
```

##### Revert

还有一种特殊情况，如果当前 commit 用于撤销以前的 commit，则必须以 `revert:`，后面跟着被撤销 Commit 的 Header.

```
revert: feat(pencil): add 'graphiteWidth' option

This reverts commit 667ecc1654a317a13331b17617d973392f415f02.
```


#### IDEA 使用 Commit Message 插件

插件名：Git Commit Template

![1](http://yuko.top:9099/images/2022/02/18/image-20210105181008428.png)
