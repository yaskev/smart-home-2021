# Организация Кода

- Называйте пулл реквест Homework + ее номер.

- В одном пулл реквесте должен присутствовать код по соответствующей домашней работе, либо по нескольким, следующим вподряд.

# Типичные Ошибки

## Общие Правила

### Выбирайте отражающие суть названия

В идеале по названию класса/метода должно быть понятно, что он делает. Чем более конкретно — тем лучше.

**Нет**

```java
boolean someAct()
boolean isSomeAct()
void manage(SmartHome home)
SmartHome read()
interface EventHandling
```

**Да**

```java
boolean isClosed() // В классе с состоянием двери
void processEvent(Event event)
```

Предлагается две стратегии для названия классов с имплементаций интерфейсов:

1. Имплементация представляет частный случай — название частного случая + название интерфейса, например `interface HomeReader, class FileHomeReader, class DatabaseHomeReader`.

2. Имплементация является стандартной/единственной — название интерфейса + постфикс Impl, например `interface EventLoopProcessor, class EventLoopProcessorImpl`.

### Называйте методы глаголом с объектом [camelCase](https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names)

**Нет**

```java
public void Execute()
```

**Да**

```java
public void executeSomething()
```

### Называйте классы существительными [UpperCamelCase](https://google.github.io/styleguide/javaguide.html#s5.2.2-class-names)

**Нет**

```java
class ReadFile
class ReadFromFile
class CheckIsHall
```

### Называйте пакеты [строчными](https://google.github.io/styleguide/javaguide.html#s5.2.1-package-names) буквами

**Нет**

```java
package ru.sbt.mipt.oop.SmartHome;
```

### Не используйте статические методы в бизнес логике

Они хорошо применяются в объектах без состояния. Например, в утильных классах.

В такой ситуации лучше создать интерфейс и пользоваться им вместо вызова статического метода конкретного класса. Это позволит в будущем лекго заменить данную имплементацию ([OCP](https://drive.google.com/file/d/0BwhCYaYDn8EgN2M5MTkwM2EtNWFkZC00ZTI3LWFjZTUtNTFhZGZiYmUzODc1/view)).

Также помните про [лишние объекты](#не-создавайте-лишних-объектов).

**Нет**

```java
public class SpecificEventCreator {
    public static Event getNextEvent() {
        ...
    }
}

public class EventCreatorConsumer {
    public void needToUseEvent() {
        Event event = SpecificEventCreator.getNextEvent();
        ...
    }
}
```

**Да**

```java
public interface EventCreator {
    public Event getNextEvent();
}

public class EventCreatorImpl implements EventCreator {
    public Event getNextEvent() {
        ...
    }
}

public class EventCreatorConsumer {
    private final EventCreator eventCreator;

    public EventCreatorConsumer(EventCreator eventCreator) {
        this.eventCreator = eventCreator;
    }

    public void needToUseEvent() {
        Event event = eventCreator.getNextEvent();
        ...
    }
}
```

### Не создавайте лишних объектов

Делайте зависимости полями в классе.

**Нет**

```java
public class Application {
    public void run() {
        Event event = getEvent();
        new MyEventProcessor().processEvent(event);
    }
}
```

**Да**

```java
public class Application {
    private final EventProcessor processor;
    
    public Application(EventProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        Event event = getEvent();
        processor.processEvent(event);
    }
}
```

Не надо создавать специальные классы, создающие коллекции объектов. Коллекции можно создавать в конфигурационном коде и сохранять в поле потребляющего класса.

**Нет**

```java
List<EventHandler> handlers = new HandlerCreator().constructHandlers();
```

**Да**

```java
List<EventHandler> handlers = Arrays.asList(new DoorEventHandler(), ...);
```

См. [asList(T...)](https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#asList(T...))

### Сделите за видимостью методов

Не нужно давать лишний доступ к внутренним переменным/методам — клиенты должны пользоваться публичным API.

Например, пользователи изменяют состояние дома посредством отправки событий. Не надо давать им возможность включать/выключать свет в доме напрямую.

**Нет**

```java
...
public void handleEvent(Event event) {
    ...
    turnOffLight();
}

public void turnOffLight() {
    ...
}
```

**Да**

```java
...
public void handleEvent(Event event) {
    ...
    turnOffLight();
}

private void turnOffLight() {
    ...
}
```

### Конфигурируйте компоненты в main или DI фреймворке

Объекты с бизнес-логикой нужно создавать вместе с зависимостями в конфигурационном коде. Это может быть `public static void main(String[] args)`, либо конфигурации выбранного [DI фреймворка](https://www.martinfowler.com/articles/injection.html). Для этого хорошо подходят классы, у которых зависимости объявлены [аргументами конструктора](#не-создавайте-лишних-объектов).

Не надо использовать отдельные Java `enum` классы для этой цели. Их вызовы в коде тяжело менять, искать.

**Нет**

```java
public enum EventProcessors {
    DOOR_EVENT_PROCESSOR {
        @Override
        public EventProcessor createProcessor() {
            return new DoorEventProcessor();
        }
    },

    LIGHT_EVENT_PROCESSOR {
        @Override
        public EventProcessor createProcessor() {
            return new LightEventProcessor();
        }
    };

    public abstract EventProcessor createProcessor();
}
```

## Задание 1

### Оставляйте в репозитории только файлы с кодом

Часто под контроль версий кода попадает скомпилированный редактором байткод (в IntelliJ Idea это директория`target/classes`). Добавьте соответствующее исключение в `.gitignore`.

### Не давайте абстракции «течь»

От англ. [leaky abstraction](https://www.joelonsoftware.com/2002/11/11/the-law-of-leaky-abstractions/). Детали имплементации не должны быть отражены в публичном API. Распространенная ошибка — добавление классов специфических исключений в сигнатуру методов интерфейса. Другим имплементациям придется работать с этими нерелевантными для них исключениями.

### Разделяйте изменение объектов и сценарии умного дома

Помните про [SRP](https://drive.google.com/file/d/0ByOwmqah_nuGNHEtcU5OekdDMkk/view). Создайте по классу на обработку события открытия/закрытия двери и обработке сценария выключения света в доме.

### Делегируйте обработку событий отдельным классам

Класс события, занимающийся его обработкой — это нарушение [SRP](https://drive.google.com/file/d/0ByOwmqah_nuGNHEtcU5OekdDMkk/view). В будущем будет появляться необходимость добавлять все больше и больше обработчиков на одни и те же события.

## Задание 2

Задача — уйти от внешней итерации и преместить эту ответственность в объекты дома.

Что должен делать `Actionable`? Он должен принимать инстанс некого `Action` и применять его к своей внутренней структуре, которую никто кроме него не знает. Это позволяет нам легко ее менять, не затрагивая при этом других клиентов.

Что должен делать `Action`? Он должен принимать инстанс некого `HomeComponent` и делать с ним заданное действие. Это можно реализовать с помощью лямбд Java.

Кто создает `Action`? Обработчики событий.

### Имплементируйте внутренний итератор

Разберитесь, в чем [разница](https://stackoverflow.com/a/224675) между **внутренним** и **внешним** итератором. Последний использовать в данном задании нельзя, поэтому не надо имплементировать [Iterable](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html).

### Закройте доступ к внутренним данным

Наряду с советами по [дизайну API](#cделите-за-видимостью-методов) учитывайте, что после добавления логики по совершению действий над объектами дома посредством внешнего итератора вам не нужно давать доступ к некоторым объектам дома (и тем более возможность их изменять через сеттеры). Например, прямой доступ к комнатам из дома не нужен, поскольку все действия теперь можно делать через `Actionable` API. Это позволяет отвязаться от внутренней структуры дома. Теперь легко при необходимости добавлять новый уровень группировки объектов. Например, объединить комнаты в этаж.

### Пользуйтесь текущей иерархией объектов дома

Не стоит выдумывать новые иерархии (`Interior`, `Premise`, ...) — этого не требуется в задании. Здесь лишняя complexity не приносит пользы, зато усложняет чтение и восприятие кода.

### Придерживайтесь предложенным структурам Action и Actionable

Эти две абстракции, описанные в задании, максимально простые и, благодаря своей абстрактности, позволяют решать широкий спектр задач. Помните про [SRP](https://drive.google.com/file/d/0ByOwmqah_nuGNHEtcU5OekdDMkk/view). Для выполнения этого задания вам не нужно ничего в них добавлять — только имплементировать.

### Итерируйтесь по всем составляющим

В имплементации [внутреннего итератора](#имплементируйте-внутренний-итератор) внутри [композитного объекта](https://refactoring.guru/ru/design-patterns/composite) не забывайте выполнять заданное действие над всеми внутренними объектами.
