export declare interface ContextInterface {
    readonly data: Partial<ContextType>;
    init: (context: Partial<ContextType>) => void;
}

export declare interface ContextType {
    spaceName: string;
    spaceClientId: string;
    ownerName: string;
    ownerAvatar: string;
    ownerMotto: string;
    ownerEmail: string;
}

export declare interface IconLinkType {
    icon: string;
    name: string;
    url: string;
}