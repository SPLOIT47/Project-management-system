# Project-management-system
## 1. Определение требований

   - ### Управление статусами и приоритетами задач.

   - ### Система уведомлений:
     - Отправка уведомлений в случае изменения статуса задачи (использовать многопоточность для обработки и отправки уведомлений).

   - ### Отчетность:
     - Генерация отчетов по выполненным задачам.

   - ### Многопоточность:
     - Использовать для оптимизации отправки уведомлений и выполнения фоновых операций.

## 2. Архитектура и дизайн проекта

   ### Общая архитектура: Луковая

   - #### Domain: 
        
   - #### Application: 
      
   - #### Infrastructure: 
      
   - #### Presentation:


   ### Используемые паттерны проектирования:
   - #### Observer:
      Для реализации системы уведомлений (подписка на обновления задач).
   - #### Facade: 
      Для распределения задач между обработчиками.

## 3. Подробный план разработки компонентов

  - Description in the future...

## 4. Реализация многопоточности
   #### 1. Система уведомлений
   - Создать отдельный поток для отправки уведомлений об изменении статуса задачи, используя ExecutorService.
   - Потоки для отправки уведомлений должны выполняться асинхронно, чтобы не блокировать основной поток выполнения приложения.

   #### 2. Фоновые задачи
   - Планирование фоновых задач (например, ежедневный отчет о статусе проектов), используя ScheduledExecutorService.