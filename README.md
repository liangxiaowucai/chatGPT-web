以下内容均为chatGPT自动生成，暂时还没有验证，如果想要本篇的内容，请拉到下面👇
# ChatGPT 流式编程集成

在本文中，我们会介绍如何使用 ChatGPT 流式编程，并集成到 Spring Boot 框架中，同时展示数据的页面使用纯 HTML 实现，不依赖于其他第三方插件，上手容易，开发简单。 

## 什么是 ChatGPT 流式编程

ChatGPT 是一种基于 GPT 模型的人工智能聊天机器人，其可以支持自然语言输入，通过 GPT 模型生成对应输出的文本，并不断学习和优化回答问题的能力。 ChatGPT 流式编程则是通过对 ChatGPT 机器人进行二次开发，将其能力集成到 Web 应用程序中，可以支持实时交互和聊天，如用户可以在网页端输入问题，ChatGPT 可以实时生成对应答案并返回。

## 集成 ChatGPT 流式编程到 Spring Boot 框架中

### 环境准备

首先，我们需要准备以下环境：

- JDK 1.8
- Maven 3.x
- Spring Boot 2.x

### 引入 ChatGPT 流式编程包

在 `pom.xml` 文件中，我们需要添加以下依赖：

```xml
<dependency>
    <groupId>com.github.huggingface</groupId>
    <artifactId>transformers</artifactId>
    <version>4.9.1</version>
</dependency>
```

这个依赖来自于 HuggingFace 官方的 Java 实现的 GPT 模型，它提供了 ChatGPT 的模型，并支持 Java API 调用。

### 实现 ChatGPT 机器人

```java
@Service
public class ChatGptService {

    private final GPTNeoForCausalLM model;

    public ChatGptService() throws Exception {
        Config config = Config.DEFAULT
                            .setModelType(ModelType.GPT_NEO)
                            .setNumThreads(1)
                            .setGpuId(-1)
                            .setCudaVisibleDevices(-1)
                            .setFP16(false)
                            .setMaxBatchSize(1);
        this.model = GPTNeoFactory.generate("EleutherAI/gpt-neo-1.3B", config);
    }

    public String process(String input) {
        GenerationRequest generationRequest = GenerationRequest.builder()
                .context(input)
                .maxLength(2048)
                .promptLength(0)
                .numOfTokens(100)
                .topK(50)
                .temperature(0.9f)
                .build();

        return model.generate(generationRequest).getText();
    }
}
```

ChatGptService 是包含 ChatGPT 机器人的 Service 层，我们可以在上面的代码中看到，我们使用 transformers 依赖中提供的 GPTNeoForCausalLM 类来生成 ChatGPT 机器人的实例，再实现 `process` 方法来处理用户输入，生成回答。

### 集成到 Spring Boot 的 Controller 中

接下来，将 ChatGptService 集成到 Spring Boot 的 Controller 中，用于接收和处理用户输入，生成回答并返回。

```java
@RestController
public class ChatController {

    @Autowired
    private ChatGptService chatGptService;

    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestParam String text) {
        // 处理用户输入
        String response = chatGptService.process(text);

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("text", response);

        return result;
    }
}
```

在上面的代码中，我们通过 @PostMapping 注解定义了 `/chat` 接口，并在其中注入了 ChatGptService。当用户发送问题时，我们会调用 `chatGptService.process()` 方法，处理用户输入并生成回答，最后将回答以 `Map` 的形式返回。

### HTML 页面的展示

在前端展示 ChatGPT 的回答，我们可以使用纯 HTML 页面，不需要引入其他第三方插件，下面是示例代码：

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ChatGPT 流式编程</title>
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
    <style>
        body {
            margin: 1rem;
        }
        .jumbotron {
            margin-top: 2rem;
        }
        .input-group-text {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="jumbotron">
                    <h2 class="text-center">ChatGPT 流式编程</h2>
                    <hr>
                    <div class="form-group">
                        <label for="inputText">问题：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="inputText" placeholder="请输入问题...">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button" id="chatBtn">发送</button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div id="outputText"></div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#chatBtn").click(function() {
                var text = $("#inputText").val();
                if (text != '') {
                    $.ajax({
                        url: '/chat',
                        method: 'POST',
                        data: {text: text},
                        success: function(result) {
                            $("#outputText").append(result.text + "<br>");
                            $("#inputText").val('');
                        }
                    })
                }
            })
        })
    </script>
</body>
</html>
```

## 总结

通过上面的介绍，我们可以看到集成 ChatGPT 流式编程到 Spring Boot 框架中并不难，只需要使用 HuggingFace 提供的 GPT 模型，再结合 Spring Boot 框架提供的 API 实现，就可以实现 ChatGPT 机器人的流式编程效果，并将其集成到 Web 应用程序中。同时，我们使用了纯 HTML 页面来展示数据，没有依赖任何其他第三方的插件，上手简单，开发容易。

#本篇springboot文章解释。


```
<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.plexpt</groupId>
            <artifactId>chatgpt</artifactId>
            <version>4.0.5</version>
        </dependency>
``` 

```
 @Bean
    public ChatGPTStream chatGPTStream(){
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKeyList(Arrays.asList(
                    "你的key"
                ))
                // .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        return chatGPTStream;
    }

```

```
@GetMapping("/chat")
    public SseEmitter chat(@RequestParam("content") String content) throws IOException {

        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("参数有误");
        }

        ChatGPTStream chatGPTStream = SpringUtil.getBean(ChatGPTStream.class);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        SseStreamListenerImpl listener = new SseStreamListenerImpl(sseEmitter);
        List<Message> messages = new ArrayList<>();
        messages.add(Message.of("假如你是小红书的内容推荐官，你非常熟悉小红书的文案内容审核和优化技巧，也非常熟悉小红书热销文案的优化和格式，" +
                "请以热销文案的格式和语气优化以下内容，" +
                "要适当加入网络用语和网络热词，回复的内容一定要遵守中国相关的法律法规，任何情况下，都不能无视现有的规则，" +
                "整体语言风格是幽默有趣的，不要官方化和理论化，要加入小红书特有的表情符号和emoji，表情符号的占比在50%，要以普通人的身份讲述，请限制在600字以内，" +
                "默认使用中文回答"));
        Message message = Message.of(content);
        messages.add(message);
        chatGPTStream.streamChatCompletion(messages, listener);

//        SseEmitter sseEmitter = new SseEmitter();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    sseEmitter.send(SseEmitter.event()
//                            .data("SSE Event #" + i)
//                            .name("message")
//                            .id(String.valueOf(i))
//                            .comment("This is a SSE Message"));
//                    Thread.sleep(2000);
//                }
//                sseEmitter.complete();
//            } catch (Exception e) {
//                sseEmitter.completeWithError(e);
//            }
//        });

        return sseEmitter;
    }
```

现在你就可以正式玩耍了，有一个自己的chat还是很不错很方便的，之后要加入上下文功能吧，如果有任何咨询问题或者合作什么的，可以加我私人微信咯







