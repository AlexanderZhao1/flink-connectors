package io.pravega.connectors.flink.table.catalog.pravega.descriptors;

import org.apache.flink.table.descriptors.CatalogDescriptorValidator;
import org.apache.flink.table.descriptors.DescriptorProperties;

public class PravegaCatalogValidator extends CatalogDescriptorValidator {
    public static final String CATALOG_TYPE_VALUE_PRAVEGA = "pravega";
    public static final String CATALOG_PRAVEGA_VERSION = "pravega-version";
    public static final String CATALOG_SERVICE_URL = PulsarOptions.SERVICE_URL_OPTION_KEY;
    public static final String CATALOG_ADMIN_URL = PulsarOptions.ADMIN_URL_OPTION_KEY;
    public static final String CATALOG_STARTUP_MODE = PulsarOptions.STARTUP_MODE_OPTION_KEY;
    public static final String CATALOG_DEFAULT_PARTITIONS = PulsarOptions.DEFAULT_PARTITIONS;

    @Override
    public void validate(DescriptorProperties properties) {
        super.validate(properties);
        properties.validateValue();
    }
}
