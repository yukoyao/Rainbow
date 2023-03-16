markdown中添加流程图
===

## Mermaid是什么？

Mermaid 是一个用于画流程图、状态图、时序图、甘特图的库，使用 JS 进行本地渲染，广泛集成于许多 Markdown 编辑器中。

## 怎么使用 Mermaid？

### 基本格式

```mermaid
graph 流程图方向[TB|LR|RL|BT]
 	    流程图内容
```

### 例子

```mermaid
 graph LR
          start[开始] --> input[输入A,B,C]
          input --> conditionA{A是否大于B}
          conditionA -- YES --> conditionC{A是否大于C}
          conditionA -- NO --> conditionB{B是否大于C}
          conditionC -- YES --> printA[输出A]
          conditionC -- NO --> printC[输出C]
          conditionB -- YES --> printB[输出B]
          conditionB -- NO --> printC[输出C]
          printA --> stop[结束]
          printC --> stop
          printB --> stop
```

### 详细说明

#### 第一行出现的
```
graph [TB|BT|LR|RL|TD]
```

* 纵向：TB：从上至下；BT：从下至上；TD：从上至下
* 横向：LR：从左至右；RL：从右至左


#### 定义框体

##### 结构:

```
	id【包围符】【显示文本】【包围符】
```

##### 示例

```mermaid
		graph TD
    	id1[带文本的矩形]
    	id2(带文本的圆角矩形)
    	id3>带文本的不对称的矩形]
    	id4{带文本的菱形}
    	id5((带文本的圆形))
```

#### 定义连接线和子图

##### 连接线结构

```
id1【连接【文本】符】id2
```

##### 子图结构

```
subgraph 子图名
	子图内容
	end
```

##### 连接线格式例子（用子图分组）

```mermaid
    	graph TB
		subgraph 实线
    	A0[A] --- B0[B] 
    	A1[A] --> B1[B]
    	A2[A] -- 描述 --> B2[B] 
    	end
    	subgraph 虚线
    	A3[A] -.- B3[B] 
   		A4[A] -.-> B4[B] 
   		A5[A] -. 描述 .-> B5[B] 
    	end
    	subgraph 加粗线
    	A6[A] === B6[B]
    	A7[A] ==> B7[B] 
    	A8[A] == 描述 ==> B8[B] 
    	end
```
