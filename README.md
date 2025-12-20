# snowflake-id-generator

__Описание__: Проект курса по k8s.

__Branches:__
* `main` - главная ветка
* `develop` - ветка разработки

## Нагрузочное тестирование
- Выполнено с помощью k6
- Script для запуска тесто находится в файле test.js

### Установка k6:
__Linux (Debian)__
```shell
sudo gpg -k  
sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt-get update
sudo apt-get install k6
```

__Windows__
- установка <a href="https://dl.k6.io/msi/k6-latest-amd64.msi" target="_blank" rel="noopener noreferrer">последняя версия через инсталлятор</a>
- прописать в PATH k6

### Установка Metrics Server
```shell
# Установка манифеста
kubectl apply -f https://raw.githubusercontent.com/kubernetes-sigs/metrics-server/master/manifests/components.yaml
```

```shell
# Флаги для работы в локальном кластере
kubectl patch deployment metrics-server -n kube-system --type='json' -p='[{"op": "add", "path": "/spec/template/spec/containers/0/args/-", "value": "--kubelet-insecure-tls"}]'
```

```shell
# Перезапуск deployment
kubectl rollout restart deployment metrics-server -n kube-system
```
__Полезные команды:__

	kubectl get pods -n kube-system -l k8s-app=metrics-server		# Проверка подов метрики сервиса
	kubectl logs -n kube-system -l k8s-app=metrics-server --tail=10		# Проверка логов
	kubectl top pods							# Tекущее использование CPU и памяти подами
	kubectl top nodes							# Tекущее использование CPU и памяти нодами
	kubectl get hpa <name-app-hpa>						# Текущее состояние Horizontal Pod Autoscaler.
		
## Результаты запуска нагрузочного тестирования
```shell
k6 run test.js

         /\      Grafana   /‾‾/                                                                                                                                                                                                                                         
    /\  /  \     |\  __   /  /                                                                                                                                                                                                                                          
   /  \/    \    | |/ /  /   ‾‾\                                                                                                                                                                                                                                        
  /          \   |   (  |  (‾)  |                                                                                                                                                                                                                                       
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: test.js
        output: -

     scenarios: (100.00%) 1 scenario, 350 max VUs, 5m30s max duration (incl. graceful stop):
              * default: Up to 350 looping VUs for 5m0s over 5 stages (gracefulRampDown: 30s, gracefulStop: 30s)



  █ THRESHOLDS 

    http_req_duration
    ✓ 'p(95)<500' p(95)=25.88ms
    ✓ 'p(99)<1500' p(99)=226.59ms

    http_req_failed
    ✓ 'rate<0.1' rate=0.53%


  █ TOTAL RESULTS 

    checks_total.......: 1068244 3559.812492/s
    checks_succeeded...: 99.73%  1065409 out of 1068244
    checks_failed......: 0.26%   2835 out of 1068244

    ✗ is status 200
      ↳  99% — ✓ 531287 / ✗ 2835
    ✓ response time OK

    HTTP
    http_req_duration..............: avg=12.94ms  min=0s    med=2.99ms   max=2.19s p(90)=12.39ms  p(95)=25.88ms 
      { expected_response:true }...: avg=12.33ms  min=0s    med=2.99ms   max=1.76s p(90)=12.19ms  p(95)=24.83ms 
    http_req_failed................: 0.53%  2835 out of 534122
    http_reqs......................: 534122 1779.906246/s

    EXECUTION
    iteration_duration.............: avg=113.66ms min=100ms med=103.49ms max=2.29s p(90)=114.08ms p(95)=126.81ms
    iterations.....................: 534122 1779.906246/s
    vus............................: 1      min=1              max=349
    vus_max........................: 350    min=350            max=350

    NETWORK
    data_received..................: 117 MB 389 kB/s
    data_sent......................: 44 MB  146 kB/s




running (5m00.1s), 000/350 VUs, 534122 complete and 0 interrupted iterations
default ✓ [======================================] 000/350 VUs  5m0s
```
