FROM grafana/grafana:10.4.2

# Copy provisioning configs
COPY provisioning/datasources /etc/grafana/provisioning/datasources
COPY provisioning/dashboards /etc/grafana/provisioning/dashboards
COPY provisioning/dashboards /etc/grafana/dashboards

# Install Redis plugin
ENV GF_INSTALL_PLUGINS=redis-datasource
ENV GF_ENABLE_GZIP=true