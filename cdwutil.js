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