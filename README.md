# Prometheus Java example
Example Java app that runs in a loop and simulates requests with different latencies and exposes a Prometheus histogram on an HTTP server

## Run
- Build the app using Gradle and JDK >= 1.8
- Simply run the main class
- `curl localhost:<PORT>` to see Prometheus metrics
- Kill the process when you're done

## Example
Check the example output and metrics at [here](https://gist.github.com/jadbaz/34e94ed6c8f6d77fdd176c889cbad06d)