var logger = {
    log: function(msg) {
        if (typeof finesse !== 'undefined' && finesse.cslogger && finesse.cslogger.ClientLogger) {
            finesse.cslogger.ClientLogger.log(msg);
        } else {
            console.log(msg);
        }
    }
};

const KEY_MAP = {
    n: "additionalHomeowner",
    E: "asg",
    o: "chatHoursOperations",
    u: "current_page_url",
    I: "decryptedLoanNumber",
    M: "documentCompletedDate",
    i: "documentStatus",
    L: "documentUploadDate",
    MC: "mortgageeClause",
    v: "paymentMethodType",
    w: "paymentDate",
    g: "paymentPremiumAmount",
    x: "paymentCarrierName",
    y: "paymentCarrierAddress",
    z: "url",
    S: "fullName",
    R: "clientName",
    O: "propertyAddress",
    Q: "contactNumber",
    p: "fax",
    q: "mailingAddress",

    // extras from PV2
    compSub: "companySubdivision",
    compSeg: "companySegment"
};

function decodeAndMap(...payloads) {
    const result = {};

    payloads.forEach(payload => {
        if (!payload || typeof payload !== "string") return;

        payload.split("|").forEach(pair => {
            if (!pair) return;

            const [rawKey, ...rest] = pair.split("=");
            const value = rest.join("=");

            const mappedKey = KEY_MAP[rawKey] || rawKey;

            result[mappedKey] = value || "";
        });
    });

    return result;
}

function mapWebPayload(webPayload) {
    logger.log("[mapWebPayload] Input type=" + typeof webPayload + " value=" + JSON.stringify(webPayload));

    if (!webPayload || typeof webPayload !== "object") {
        logger.log("[mapWebPayload] WARNING: invalid or empty webPayload, returning {}");
        return {};
    }

    var inputKeys = Object.keys(webPayload);
    logger.log("[mapWebPayload] Input keys (" + inputKeys.length + "): " + inputKeys.join(", "));

    var unmappedKeys = [];
    const result = {};
    Object.keys(webPayload).forEach(function(key) {
        var mappedKey = KEY_MAP[key] || key;
        if (!KEY_MAP[key]) unmappedKeys.push(key);
        result[mappedKey] = webPayload[key] != null ? webPayload[key] : "";
    });

    if (unmappedKeys.length) {
        logger.log("[mapWebPayload] Keys not in KEY_MAP (passed through as-is): " + unmappedKeys.join(", "));
    }
    logger.log("[mapWebPayload] Result (" + Object.keys(result).length + " keys): " + JSON.stringify(result));
    return result;
}