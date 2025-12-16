import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
	stages: [
		{ duration: '30s', target: 100 },
		{ duration: '1m', target: 200 },
		{ duration: '2m', target: 350 },
		{ duration: '1m', target: 150 },
		{ duration: '30s', target: 0 },
	],

	thresholds: {
		http_req_duration: ['p(95)<500', 'p(99)<1500'],
		http_req_failed: ['rate<0.1'],
	},
};

export default function () {
	const res = http.get(`http://snowflake.dev.local/next-id`);

	check(res, {
		'is status 200': (r) => r.status === 200,
		'response time OK': (r) => r.timings.duration < 3000,
	});

	sleep(0.1);
}
